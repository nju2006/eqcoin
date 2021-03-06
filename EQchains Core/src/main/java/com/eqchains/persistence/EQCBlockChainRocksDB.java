///**
// * EQchains core - EQchains Federation's EQchains core library
// * @copyright 2018-present EQchains Federation All rights reserved...
// * Copyright of all works released by EQchains Federation or jointly released by
// * EQchains Federation with cooperative partners are owned by EQchains Federation
// * and entitled to protection available from copyright law by country as well as
// * international conventions.
// * Attribution — You must give appropriate credit, provide a link to the license.
// * Non Commercial — You may not use the material for commercial purposes.
// * No Derivatives — If you remix, transform, or build upon the material, you may
// * not distribute the modified material.
// * For any use of above stated content of copyright beyond the scope of fair use
// * or without prior written permission, EQchains Federation reserves all rights to
// * take any legal action and pursue any right or remedy available under applicable
// * law.
// * https://www.eqchains.com
// * 
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// *//*
//package com.eqchains.persistence;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.Vector;
//
//import org.rocksdb.ColumnFamilyDescriptor;
//import org.rocksdb.ColumnFamilyHandle;
//import org.rocksdb.ColumnFamilyOptions;
//import org.rocksdb.CompressionType;
//import org.rocksdb.DBOptions;
//import org.rocksdb.MutableColumnFamilyOptions;
//import org.rocksdb.Options;
//import org.rocksdb.ReadOptions;
//import org.rocksdb.RocksDB;
//
//import org.rocksdb.RocksIterator;
//import org.rocksdb.WALRecoveryMode;
//import org.rocksdb.WriteBatch;
//import org.rocksdb.WriteOptions;
//
//import com.eqchains.blockchain.account.Account;
//import com.eqchains.blockchain.account.Passport;
//import com.eqchains.blockchain.accountsmerkletree.Filter.Mode;
//import com.eqchains.blockchain.hive.EQCHive;
//import com.eqchains.blockchain.subchain.EQCSubchain;
//import com.eqchains.blockchain.account.AssetSubchainAccount;
//import com.eqchains.blockchain.transaction.Transaction;
//import com.eqchains.persistence.EQCBlockChainH2.NODETYPE;
//import com.eqchains.rpc.Balance;
//import com.eqchains.rpc.IPList;
//import com.eqchains.rpc.MaxNonce;
//import com.eqchains.rpc.Nest;
//import com.eqchains.rpc.SignHash;
//import com.eqchains.rpc.TransactionIndex;
//import com.eqchains.rpc.TransactionIndexList;
//import com.eqchains.rpc.TransactionList;
//import com.eqchains.serialization.EQCType;
//import com.eqchains.util.ID;
//import com.eqchains.util.Log;
//import com.eqchains.util.Util;
//import com.eqchains.util.Util.AddressTool;
//
//
//*//**
// * @author Xun Wang
// * @date Mar 11, 2019
// * @email 10509759@qq.com
// *//*
//public class EQCBlockChainRocksDB implements EQCBlockChain {
//	private static EQCBlockChainRocksDB instance;
//	private RocksDB rocksDB;
//	private List<ColumnFamilyHandle> columnFamilyHandles;
//	private List<byte[]> defaultColumnFamilyNames;
//	private List<ColumnFamilyDescriptor> columnFamilyDescriptors;
//	public static final byte[] SUFFIX_AI = "AI".getBytes();
//	public static final byte[] SUFFIX_HASH = "Hash".getBytes();
//	public static final byte[] MISC_TABLE = "Misc".getBytes();
//	public static final byte[] PREFIX_H = "H".getBytes();
//	public static final byte[] EQCBLOCK_TAIL_HEIGHT = "EQCBlockTailHeight".getBytes();
//	
//	public enum TABLE {
//		DEFAULT, 
//		EQCBLOCK, EQCBLOCK_HASH, 
//		ACCOUNT, ACCOUNT_AI, ACCOUNT_HASH, 
//		ACCOUNT_MINERING, ACCOUNT_MINERING_AI, ACCOUNT_MINERING_HASH, 
//		ACCOUNT_VALID, ACCOUNT_VALID_AI, ACCOUNT_VALID_HASH, 
//		MISC
//	}
//
//	static {
//		RocksDB.loadLibrary();
//	}
//
//	private EQCBlockChainRocksDB() throws RocksDBException {
//		defaultColumnFamilyNames = Arrays.asList(
//				RocksDB.DEFAULT_COLUMN_FAMILY, 
//				TABLE.EQCBLOCK.name().getBytes(), TABLE.EQCBLOCK_HASH.name().getBytes(),
//				TABLE.ACCOUNT.name().getBytes(), TABLE.ACCOUNT_AI.name().getBytes(), TABLE.ACCOUNT_HASH.name().getBytes(), 
//				TABLE.ACCOUNT_MINERING.name().getBytes(), TABLE.ACCOUNT_MINERING_AI.name().getBytes(), TABLE.ACCOUNT_MINERING_HASH.name().getBytes(), 
//				TABLE.ACCOUNT_VALID.name().getBytes(), TABLE.ACCOUNT_VALID_AI.name().getBytes(), TABLE.ACCOUNT_VALID_HASH.name().getBytes(), 
//				TABLE.MISC.name().getBytes());
//		List<byte[]> columnFamilyNames = new ArrayList<>();
//		columnFamilyNames.addAll(defaultColumnFamilyNames);
//		columnFamilyHandles = new ArrayList<>();
//		final DBOptions dbOptions = new DBOptions().setCreateIfMissing(true).setCreateMissingColumnFamilies(true).setStatsDumpPeriodSec(0).setWalRecoveryMode(WALRecoveryMode.AbsoluteConsistency);
//			for(byte[] bytes: RocksDB.listColumnFamilies(new Options(), Util.ROCKSDB_PATH)) {
//				if(!isDefaultColumnFamily(bytes)) {
//					columnFamilyNames.add(bytes);
//				}
//			}
//			final List<ColumnFamilyDescriptor> columnFamilyDescriptors = new Vector();
//			ColumnFamilyOptions columnFamilyOptions = null;
//			for(byte[] bytes : columnFamilyNames) {
//				columnFamilyOptions = new ColumnFamilyOptions();
//				columnFamilyOptions.setCompressionType(CompressionType.NO_COMPRESSION);
//				columnFamilyDescriptors.add(new ColumnFamilyDescriptor(bytes, columnFamilyOptions));
//			}
//			rocksDB = RocksDB.open(dbOptions, Util.ROCKSDB_PATH, columnFamilyDescriptors,
//					columnFamilyHandles);
//			List<ColumnFamilyHandle> tempColumnFamilyHandles = new ArrayList<>();
//			for(int i=0; i<columnFamilyNames.size(); ++i) {
//				Log.info("No." + i + "'s columnFamilyNames " + new String(columnFamilyNames.get(i)));
//				if(!isDefaultColumnFamily(columnFamilyNames.get(i))) {
//					Log.info("Exists undefault table " + new String(columnFamilyDescriptors.get(i).getName()) + " just clear it.");
//					clearTable(columnFamilyHandles.get(i));
//					dropTable(columnFamilyHandles.get(i));
//					columnFamilyHandles.get(i).close();
//					tempColumnFamilyHandles.add(columnFamilyHandles.get(i));
//				}
//			}
//			if(!tempColumnFamilyHandles.isEmpty()) {
//				Log.info("Exists undefault ColumnFamilyHandles just remove it");
//				columnFamilyHandles.removeAll(tempColumnFamilyHandles);
//			}
//	}
//	
//	public  static EQCBlockChainRocksDB getInstance() throws RocksDBException {
//		if (instance == null) {
//			synchronized (EQCBlockChainRocksDB.class) {
//				if (instance == null) {
//					instance = new EQCBlockChainRocksDB();
//				}
//			}
//		}
//		return instance;
//	}
//	
//	public   void batchUpdate(WriteBatch writeBatch) throws RocksDBException {
//		rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//	}
//
//	public   RocksIterator getRocksIterator (TABLE table) {
//		return rocksDB.newIterator(getTableHandle(table));
//	}
//	
//	public   boolean isDefaultColumnFamily(byte[] columnFamilyName) {
//		boolean isExists = false;
//		for(byte[] bytes : defaultColumnFamilyNames) {
//			if(Arrays.equals(bytes, columnFamilyName)) {
//				return true;
//			}
//		}
//		return isExists;
//	}
//	
//	public   ColumnFamilyHandle getTableHandle(TABLE table) {
//		return columnFamilyHandles.get(table.ordinal());
//	}
//	
//	public  void put(TABLE table, byte[] key, byte[] value) throws RocksDBException {
//		WriteBatch writeBatch = new WriteBatch();
//		writeBatch.put(getTableHandle(table), key, value);
//		rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//	}
//
//	public  byte[] get(TABLE table, byte[] key) throws RocksDBException {
//		return rocksDB.get(getTableHandle(table), key);
//	}
//	
//	public   void delete(TABLE table, byte[] key) throws RocksDBException {
//		WriteBatch writeBatch = new WriteBatch();
//		writeBatch.delete(getTableHandle(table), key);
//		rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//	}
//	
//	public   ColumnFamilyHandle  createTable(byte[] columnFamilyName) throws RocksDBException {
//		ColumnFamilyHandle columnFamilyHandle = null;
//			columnFamilyHandle = rocksDB.createColumnFamily(new ColumnFamilyDescriptor(columnFamilyName));
//		return columnFamilyHandle;
//	}
//	
//	public   void dropTable(ColumnFamilyHandle columnFamilyHandle) throws IllegalArgumentException, RocksDBException {
//			rocksDB.dropColumnFamily(columnFamilyHandle);
//	}
//	
//	public   void clearTable(ColumnFamilyHandle columnFamilyHandle) throws RocksDBException {
//		WriteBatch writeBatch =  new WriteBatch();
//		boolean isExistsObject = false;
//		RocksIterator rocksIterator = rocksDB.newIterator(columnFamilyHandle);
//		rocksIterator.seekToFirst();
//		isExistsObject = rocksIterator.isValid();
//		while(rocksIterator.isValid()) {
//				Log.info("Begin delete No. " + rocksIterator.key());
//				writeBatch.delete(columnFamilyHandle, rocksIterator.key());
//				rocksIterator.next();
//		}
//		if(isExistsObject) {
//			Log.info("Exists object in table " + columnFamilyHandle.getName() + " begin delete it");
//			rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//		}
//	}
//	
//	public   ID getTableItemNumbers(ColumnFamilyHandle columnFamilyHandle) {
//		ID id = ID.ZERO;
//		RocksIterator rocksIterator = rocksDB.newIterator(columnFamilyHandle);
//		rocksIterator.seekToFirst();
//		while(rocksIterator.isValid()) {
//			id = id.getNextID();
//			rocksIterator.next();
//		}
//		return id;
//	}
//	
//	public   ID getAccountNumbers() {
//		return getTableItemNumbers(getTableHandle(TABLE.ACCOUNT));
//	}
//	
//	public   ID getEQCBlockNumbers() {
//		return getTableItemNumbers(getTableHandle(TABLE.EQCBLOCK));
//	}
//	
//	@Deprecated
//	public   void deleteAddress(Passport passport) throws RocksDBException {
//		WriteBatch writeBatch = new WriteBatch();
//			writeBatch.delete(getTableHandle(TABLE.ACCOUNT), passport.getId().getEQCBits());
//			writeBatch.delete(getTableHandle(TABLE.ACCOUNT), passport.getAddressAI());
//			writeBatch.delete(getTableHandle(TABLE.ACCOUNT), addPrefixH(passport.getId().getEQCBits()));
//			rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//	}
//
//	@Deprecated
//	public   boolean deleteAddressFromHeight(ID height) {
//		boolean isSucc = true;
//		WriteBatch writeBatch = new WriteBatch();
//		try {
//			ID serialNumber = ID.ZERO;
//			byte[] bytes = null;
//			for(int i=0; i<height.longValue(); ++i) {
//				bytes = rocksDB.get(getTableHandle(TABLE.ACCOUNT), new ReadOptions(), serialNumber.getEQCBits());
//				Account account = Account.parseAccount(bytes);
//				writeBatch.delete(account.getPassport().getId().getEQCBits());
//				writeBatch.delete(account.getPassport().getAddressAI());
//				writeBatch.delete(addPrefixH(account.getPassport().getId().getEQCBits()));
//			}
//			rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//		} catch (RocksDBException | NoSuchFieldException | IllegalStateException | IOException e) {
//			isSucc = false;
//			e.printStackTrace();
//			Log.Error("During deleteAddressFromHeight " + height.toString() + " error occur: " + e.getMessage());
//		}
//		return isSucc;
//	}
//
//	public   static byte[] addPrefixH(byte[] bytes) {
//		return addPrefix(PREFIX_H, bytes);
//	}
//	
//	public   static byte[] addPrefix(byte[] prefix, byte[] bytes) {
//		return ByteBuffer.allocate(prefix.length + bytes.length).put(prefix).put(bytes).array();
//	}
//	
//	public   static byte[] addSuffix(byte[] bytes, byte[] suffix) {
//		return ByteBuffer.allocate(bytes.length + suffix.length).put(bytes).put(suffix).array();
//	}
//
//	public   boolean saveAccount(Account account) throws Exception {
//		WriteBatch writeBatch = new WriteBatch();
//			writeBatch.put(getTableHandle(TABLE.ACCOUNT), account.getId().getEQCBits(), account.getBytes());
////			Log.info(account.toString());
////			Log.info(Util.dumpBytes(account.getAddress().getAddressAI(), 16));
//			writeBatch.put(getTableHandle(TABLE.ACCOUNT_AI), account.getPassport().getAddressAI(), account.getId().getEQCBits());
//			writeBatch.put(getTableHandle(TABLE.ACCOUNT_HASH), account.getId().getEQCBits(), account.getHash());
//			rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//			return false;
//	}
//
//	public  boolean deleteAccount(ID id)
//			throws NoSuchFieldException, IllegalStateException, IOException {
//		WriteBatch writeBatch = new WriteBatch();
//		Account account = Account.parseAccount(Objects.requireNonNull(get(TABLE.ACCOUNT, id.getEQCBits())));
//		writeBatch.delete(getTableHandle(TABLE.ACCOUNT), id.getEQCBits());
//		writeBatch.delete(getTableHandle(TABLE.ACCOUNT_AI), account.getPassport().getAddressAI());
//		writeBatch.delete(getTableHandle(TABLE.ACCOUNT_HASH), id.getEQCBits());
//		rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//		return false;
//	}
//	
//	public  void deleteAccountFromTo(ID fromID, ID toID) throws Exception {
//		WriteBatch writeBatch = new WriteBatch();
//		Account account = null;
//		ID id = null;
//		for(long i=fromID.longValue(); i<=toID.longValue(); ++i) {
//			id = ID.valueOf(i);
//			account = Account.parseAccount(Objects.requireNonNull(get(TABLE.ACCOUNT, id.getEQCBits())));
//			writeBatch.delete(getTableHandle(TABLE.ACCOUNT), id.getEQCBits());
//			writeBatch.delete(getTableHandle(TABLE.ACCOUNT_AI), account.getPassport().getAddressAI());
//			writeBatch.delete(getTableHandle(TABLE.ACCOUNT_HASH), id.getEQCBits());
//		}
//		rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//	}
//	
//	public   Account getAccount(ID id) throws NoSuchFieldException, IllegalStateException, IOException {
//		Account account = null;
//		byte[] bytes = null;
//		bytes = get(TABLE.ACCOUNT, id.getEQCBits());
//		if(bytes == null) {
//			throw new NullPointerException("No. " + id + " is NULL");
//		}
//		account = Account.parseAccount(bytes);
//		return account;
//	}
//	
//	public   ID getAccountID(byte[] addressAI) throws RocksDBException {
//		ID id = null;
//		byte[] bytes = null;
//		bytes = get(TABLE.ACCOUNT_AI, addressAI);
//		if (bytes != null) {
//			id = new ID(bytes);
//		}
//		return id;
//	}
//	
//	public   byte[] getAccountHash(ID id) throws RocksDBException {
//		byte[] bytes = null;
//			bytes = get(TABLE.ACCOUNT_HASH, id.getEQCBits());
//		return bytes;
//	}
//
//	@Override
//	public   EQCHive getEQCHive(ID height, boolean isSegwit) throws Exception {
//		EQCHive eqcHive = null;
//		byte[] bytes = null;
//		if ((bytes = get(TABLE.EQCBLOCK, height.getEQCBits())) != null) {
//			eqcHive = new EQCHive(bytes, isSegwit);
//		}
//		return eqcHive;
//	}
//	
//	@Override
//	public   boolean saveEQCHive(EQCHive eqcHive) throws RocksDBException {
//			Objects.requireNonNull(eqcHive);
//			WriteBatch writeBatch = new WriteBatch();
//			writeBatch.put(getTableHandle(TABLE.EQCBLOCK), eqcHive.getHeight().getEQCBits(), eqcHive.getBytes());
//			writeBatch.put(getTableHandle(TABLE.EQCBLOCK_HASH), eqcHive.getHeight().getEQCBits(), eqcHive.getEqcHeader().getHash());
//			rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//			return false;
//	}
//
//	@Override
//	public   boolean deleteEQCHive(ID height) throws RocksDBException {
//			WriteBatch writeBatch = new WriteBatch();
//			writeBatch.delete(getTableHandle(TABLE.EQCBLOCK), height.getEQCBits());
//			writeBatch.delete(getTableHandle(TABLE.EQCBLOCK_HASH), height.getEQCBits());
//			rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//			return false;
//	}
//	
//	public   void deleteEQCHiveFromTo(ID fromHeight, ID toHeight) throws RocksDBException {
//			WriteBatch writeBatch = new WriteBatch();
//			ID id = null;
//			for(long i=fromHeight.longValue(); i<=toHeight.longValue(); ++i) {
//				id = ID.valueOf(i);
//				writeBatch.delete(getTableHandle(TABLE.EQCBLOCK), id.getEQCBits());
//				writeBatch.delete(getTableHandle(TABLE.EQCBLOCK_HASH), id.getEQCBits());
//			}
//			rocksDB.write(new WriteOptions().setSync(true), writeBatch);
//	}
//
//	@Override
//	public   Vector<Transaction> getTransactionListInPool() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   boolean saveTransactionInPool(Transaction transaction) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean deleteTransactionInPool(Transaction transaction) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean deleteTransactionsInPool(EQCHive eqcBlock) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean isTransactionExistsInPool(Transaction transaction) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   byte[] getEQCHeaderHash(ID height) throws RocksDBException {
//		byte[] bytes = null;
//		 bytes = get(TABLE.EQCBLOCK_HASH, height.getEQCBits());
//		return bytes;
//	}
//
//	@Override
//	public   ID getEQCBlockTailHeight() throws RocksDBException {
//		ID id = null;
//		id = new ID(get(TABLE.MISC, EQCBLOCK_TAIL_HEIGHT));
//		return id;
//	}
//
//	@Override
//	public   boolean saveEQCBlockTailHeight(ID height) throws RocksDBException {
//			Objects.requireNonNull(height);
//			put(TABLE.MISC, EQCBLOCK_TAIL_HEIGHT, height.getEQCBits());
//			return false;
//	}
//	
//	@Override
//	public  boolean close() {
//		if (columnFamilyHandles != null) {
//			for (ColumnFamilyHandle columnFamilyHandle : columnFamilyHandles) {
//				columnFamilyHandle.close();
//			}
//			columnFamilyHandles = null;
//		}
//		if (rocksDB != null) {
//			rocksDB.close();
//			rocksDB = null;
//		}
//		return true;
//	}
//	
//	 (non-Javadoc)
//	 * @see java.lang.Object#finalize()
//	 
//	@Override
//	protected void finalize() throws Throwable {
//		close();
//	}
//
//	public   Account getAccount(byte[] addressAI)
//			throws NoSuchFieldException, IllegalStateException, IOException {
//		Account account = null;
//		byte[] bytes = null;
//		bytes = get(TABLE.ACCOUNT_AI, addressAI);
////		if (bytes == null) {
////			throw new NullPointerException(AddressTool.AIToAddress(addressAI) + " relevant Account ID is NULL");
////		}
//		if (bytes != null) {
////			Log.info("ID: " + new ID(bytes));
//			bytes = get(TABLE.ACCOUNT, bytes);
//			if (bytes == null) {
//				throw new NullPointerException(AddressTool.AIToAddress(addressAI) + " relevant Account is NULL");
//			}
//			account = Account.parseAccount(bytes);
//		}
//		return account;
//	}
//
//	@Override
//	public   ID getTotalAccountNumbers(ID height) throws Exception {
//		EQCType.assertNotBigger(height, getEQCBlockTailHeight());
//		AssetSubchainAccount assetSubchainAccount = null;
//
//		if (height.compareTo(getEQCBlockTailHeight()) < 0) {
//			assetSubchainAccount = (AssetSubchainAccount) EQCBlockChainH2.getInstance().getAccountSnapshot(ID.ONE,
//					height);
//		} else {
//			assetSubchainAccount = (AssetSubchainAccount) getAccount(ID.ONE);
//		}
//		return assetSubchainAccount.getAssetSubchainHeader().getTotalAccountNumbers();
//	}
//
//	@Override
//	public   Account getAccountSnapshot(ID accountID, ID height) {
//		return null;
//	}
//
//	@Override
//	public   boolean saveAccountSnapshot(Account account, ID height) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean deleteAccountSnapshotFrom(ID height, boolean isForward) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean dropTable() throws RocksDBException {
//		for (ColumnFamilyHandle columnFamilyHandle : columnFamilyHandles) {
//				clearTable(columnFamilyHandle);
//				if(!Arrays.equals(RocksDB.DEFAULT_COLUMN_FAMILY, columnFamilyHandle.getName())) {
//					dropTable(columnFamilyHandle);
//				}
//		}
//		return false;
//	}
//	
//	public   void dumpEQCBlock() throws Exception {
//		ID tail = getEQCBlockNumbers();
//		Log.info("Current have " + tail + " blocks.");
//		for(int i=0; i<tail.longValue(); ++i) {
//			Log.info(getEQCHive(new ID(i), false).toString());
//			Log.info("EQCHeader's Hash: " + Util.dumpBytes(getEQCHeaderHash(new ID(i)), 16));
//		}
//	}
//	
//	public   void dumpAccount() throws Exception {
//		ID tail = getTotalAccountNumbers(getEQCBlockTailHeight());
//		Log.info("Current have " + tail + " Accounts.");
//		Account account = null;
//		for(int i=1; i<=tail.longValue(); ++i) {
//			account = getAccount(new ID(i));
//			Log.info(account.toString());
//			Log.info(Util.dumpBytes(account.getPassport().getAddressAI(), 16));
//			Log.info("ID: " + getAccountID(account.getPassport().getAddressAI()).toString());
//			Log.info("Account's Hash: " + Util.dumpBytes(getAccountHash(account.getId()), 16));
//		}
//	}
//	
//	public   void dumpAccountID() {}
//
//	public   ID dumpTable(TABLE table) {
//		ID tail = getTableItemNumbers(getTableHandle(table));
//		Log.info(table + " have " + tail + " elements.");
//		
//		ID id = ID.ZERO;
//		RocksIterator rocksIterator = rocksDB.newIterator(getTableHandle(table));
//		rocksIterator.seekToFirst();
//		while (rocksIterator.isValid()) {
//			if (table == TABLE.ACCOUNT) {
//				Log.info("Key: " + new ID(rocksIterator.key()));
//				Account account;
//				try {
//					account = Account.parseAccount(rocksIterator.value());
//					Log.info("Value: " + account.toString());
//					Log.info("AddressAI: " + Util.dumpBytes(account.getPassport().getAddressAI(), 16));
//				} catch (NoSuchFieldException | IllegalStateException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					Log.Error(e.getMessage());
//				}
//			} else if (table == TABLE.ACCOUNT_AI) {
//				Log.info("Key: " + Util.dumpBytes(rocksIterator.key(), 16));
//				Log.info("Value: " + new ID(rocksIterator.value()).toString());
//			}
//			rocksIterator.next();
//		}
//		return id;
//	}
//
//	@Override
//	public   byte[] getEQCHeaderBuddyHash(ID height, ID currentHeight) throws Exception {
//		byte[] hash = null;
//		ID tail = getEQCBlockTailHeight();
//		// Due to the latest Account is got from current node so it's xxxUpdateHeight doesn't higher than tail
////		EQCType.assertNotBigger(height, tail);
//		if(height.compareTo(tail) <= 0) {
//			hash = EQCBlockChainRocksDB.getInstance().getEQCHeaderHash(height);
//		}
//		else if(height.equals(tail.getNextID())){
//			hash = EQCBlockChainRocksDB.getInstance().getEQCHeaderHash(tail);
//		}
//		else {
//			throw new IllegalArgumentException("Height " + height + " shouldn't bigger than tail " + tail + " more than one");
//		}
//		return hash;
//	}
//
//	@Override
//	public   boolean isMinerExists(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean saveMiner(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean deleteMiner(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   IPList getMinerList() throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   boolean isFullNodeExists(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean saveFullNode(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean deleteFullNode(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   IPList getFullNodeList() throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   boolean isIPExists(String ip, NODETYPE nodeType) throws SQLException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean isTransactionMaxNonceExists(Nest nest) throws SQLException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   boolean saveTransactionMaxNonce(Nest nest, MaxNonce maxNonce) throws SQLException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   MaxNonce getTransactionMaxNonce(Nest nest) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   boolean deleteTransactionMaxNonce(Nest nest) throws SQLException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   Balance getBalance(Nest nest) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   Vector<Transaction> getPendingTransactionListInPool(Nest nest) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   TransactionIndexList getTransactionIndexListInPool(long previousSyncTime, long currentSyncTime) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   TransactionList getTransactionListInPool(TransactionIndexList transactionIndexList)
//			throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   boolean saveMinerSyncTime(String ip, long syncTime) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public   long getMinerSyncTime(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public   SignHash getSignHash(ID id) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public   Account getAccountSnapshot(byte[] addressAI, ID height) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean saveIPCounter(String ip, int counter) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int getIPCounter(String ip) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public boolean isTransactionExistsInPool(TransactionIndex transactionIndex) throws SQLException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean saveAccount(Account account, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Account getAccount(ID id, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean clear(Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Account getAccount(byte[] addressAI, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean merge(Mode mode) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean takeSnapshot(Mode mode, ID height) throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isEQCHiveExists(ID height) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public ID isTransactionExists(Transaction transaction, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean saveTransaction(Transaction transaction, ID height, ID index, ID sn, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean deleteTransaction(Transaction transaction, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean deleteTransactionFrom(ID height, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean deleteAccount(ID id, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Account getAccount(ID id, ID height) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean saveTransactions(EQCSubchain eqcSubchain, ID height, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean saveTransactions(EQCHive eqcHive, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public ID getTotalTransactionNumbers(ID height, ID assetID, Mode mode) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
//*/