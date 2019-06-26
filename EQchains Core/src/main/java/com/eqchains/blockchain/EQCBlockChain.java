/**
 * EQchains core - EQchains Foundation's EQchains core library
 * @copyright 2018-present EQchains Foundation All rights reserved...
 * Copyright of all works released by EQchains Foundation or jointly released by
 * EQchains Foundation with cooperative partners are owned by EQchains Foundation
 * and entitled to protection available from copyright law by country as well as
 * international conventions.
 * Attribution — You must give appropriate credit, provide a link to the license.
 * Non Commercial — You may not use the material for commercial purposes.
 * No Derivatives — If you remix, transform, or build upon the material, you may
 * not distribute the modified material.
 * For any use of above stated content of copyright beyond the scope of fair use
 * or without prior written permission, EQchains Foundation reserves all rights to
 * take any legal action and pursue any right or remedy available under applicable
 * law.
 * https://www.eqchains.com
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.eqchains.blockchain;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Vector;

import org.rocksdb.RocksDBException;

import com.eqchains.blockchain.account.Account;
import com.eqchains.blockchain.account.Passport;
import com.eqchains.blockchain.transaction.Transaction;
import com.eqchains.rpc.Max;
import com.eqchains.util.ID;

/**
 * @author Xun Wang
 * @date Oct 2, 2018
 * @email 10509759@qq.com
 */
public interface EQCBlockChain {
	
	// Account relevant interface for H2, avro(optional).
	public void saveAccount(Account account) throws Exception;
	
	public Account getAccount(ID id) throws Exception;
	
	public Account getAccount(byte[] addressAI) throws Exception;
	
	public void deleteAccount(ID id) throws Exception;
	
	// Block relevant interface for for avro, H2(optional).
	public void saveEQCBlock(EQCHive eqcBlock) throws Exception;
	
	public EQCHive getEQCBlock(ID height, boolean isSegwit) throws Exception;
	
	public void deleteEQCBlock(ID height) throws Exception;
	
	// TransactionPool relevant interface for H2, avro.
	public Vector<Transaction> getTransactionListInPool() throws SQLException, Exception;
	
	public boolean saveTransactionInPool(Transaction transaction) throws SQLException;
	
	public boolean deleteTransactionInPool(Transaction transaction) throws SQLException;
	
	public boolean deleteTransactionsInPool(EQCHive eqcBlock) throws SQLException;
	
	public boolean isTransactionExistsInPool(Transaction transaction) throws SQLException;
	
	public Max getTransactionMax(ID id) throws SQLException;
	
	public boolean saveTransactionMax(ID id, Max max) throws SQLException;
	
	// For sign and verify Transaction need use relevant TxIn's EQC block header's hash via this function to get it from xxx.EQC.
	public byte[] getEQCHeaderHash(ID height) throws Exception;
	
	public byte[] getEQCHeaderBuddyHash(ID height) throws Exception;
	
	public ID getEQCBlockTailHeight() throws Exception;
	
	public void saveEQCBlockTailHeight(ID height) throws Exception;
	
	public ID getTotalAccountNumbers(ID height) throws Exception;
	
	// Release the relevant database resource
	public boolean close() throws SQLException, Exception;
	
	// Clear the relevant database table
	public void dropTable() throws Exception, SQLException;
	
	// Take Account's snapshot
	public Account getAccountSnapshot(ID accountID, ID height) throws SQLException, Exception;
	
	public boolean saveAccountSnapshot(Account account, ID height) throws SQLException, Exception;
	
	public boolean deleteAccountSnapshot(ID height, boolean isForward) throws SQLException, Exception;
	
}
