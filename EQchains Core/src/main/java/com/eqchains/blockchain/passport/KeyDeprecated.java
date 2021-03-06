/**
 * EQcoin core - EQcoin Federation's EQcoin core library
 * @copyright 2018-present EQcoin Federation All rights reserved...
 * Copyright of all works released by EQcoin Federation or jointly released by
 * EQcoin Federation with cooperative partners are owned by EQcoin Federation
 * and entitled to protection available from copyright law by country as well as
 * international conventions.
 * Attribution — You must give appropriate credit, provide a link to the license.
 * Non Commercial — You may not use the material for commercial purposes.
 * No Derivatives — If you remix, transform, or build upon the material, you may
 * not distribute the modified material.
 * For any use of above stated content of copyright beyond the scope of fair use
 * or without prior written permission, EQcoin Federation reserves all rights to
 * take any legal action and pursue any right or remedy available under applicable
 * law.
 * https://www.eqcoin.org
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
package com.eqchains.blockchain.passport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.eqchains.blockchain.accountsmerkletree.PassportsMerkleTree;
import com.eqchains.blockchain.passport.Passport.AccountType;
import com.eqchains.serialization.EQCHashInheritable;
import com.eqchains.serialization.EQCHashTypable;
import com.eqchains.serialization.EQCInheritable;
import com.eqchains.serialization.EQCTypable;
import com.eqchains.serialization.EQCType;
import com.eqchains.serialization.SoleUpdate;
import com.eqchains.util.ID;
import com.eqchains.util.Log;
import com.eqchains.util.Util;

/**
 * @author Xun Wang
 * @date Dec 14, 2018
 * @email 10509759@qq.com
 */
public class KeyDeprecated implements EQCHashTypable, EQCHashInheritable  {
	
	private Lock key;
	private ID passportCreateHeight;
	private Publickey publickey;
	
	public KeyDeprecated() {
		publickey = new Publickey();
	}
	
	public KeyDeprecated(byte[] bytes) throws NoSuchFieldException, IOException {
		EQCType.assertNotNull(bytes);
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		parseBody(is);
		EQCType.assertNoRedundantData(is);
	}
	
	public KeyDeprecated(ByteArrayInputStream is) throws NoSuchFieldException, IOException {
		parseBody(is);
	}
	
	/**
	 * @return the Publickey
	 */
	public Publickey getCompressedPublickey() {
		return publickey;
	}
	/**
	 * @param CompressedPublickey the Publickey to set
	 */
	public void setPublickey(Publickey publickey) {
		this.publickey = publickey;
	}
	/**
	 * @return the Passport
	 */
	public Lock getKey() {
		return key;
	}

	/**
	 * @param Lock the Passport to set
	 */
	public void setKey(Lock passport) {
		this.key = key;
	}

	/**
	 * @return the PassportCreateHeight
	 */
	public ID getLockUpdateHeight() {
		return passportCreateHeight;
	}

	/**
	 * @param PassportCreateHeight the PassportCreateHeight to set
	 */
	public void setLockUpdateHeight(ID passportCreateHeight) {
		this.passportCreateHeight = passportCreateHeight;
	}

	@Override
	public byte[] getBytes() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			os.write(getBodyBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error(e.getMessage());
		}
		return os.toByteArray();
	}

	@Override
	public byte[] getBin() {
		return EQCType.bytesToBIN(getBytes());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\n" +
				toInnerJson() +
				"\n}";
	}
	public String toInnerJson() {
		return "\"Key\":" + "\n{\n" + 
				key.toInnerJson() + ",\n" +
				"\"AddressCreateHeight\":" + "\"" + passportCreateHeight + "\"" + ",\n" +
				((publickey.isNULL())?Publickey.NULL():publickey.toInnerJson()) + "\n" +
				"\n" + "}";
	}
	@Override
	public boolean isSanity() {
		if(key == null || passportCreateHeight == null || publickey == null) {
			return false;
		}
		if(!key.isSanity(null)) {
			return false;
		}
		if(!passportCreateHeight.isSanity()) {
			return false;
		}
		if(!publickey.isSanity()) {
			return false;
		}
		return true;
	}

	public void parseHeader(ByteArrayInputStream is) throws NoSuchFieldException, IOException {
	}

	public void parseBody(ByteArrayInputStream is) throws NoSuchFieldException, IOException {
		// Parse Passport
		key = new Lock(is);
		// Parse addressCreateHeight
		passportCreateHeight = new ID(EQCType.parseEQCBits(is));
		// Parse publickey
		publickey = new Publickey(is);
	}

	public boolean isValid(PassportsMerkleTree accountsMerkleTree) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] getHeaderBytes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBodyBytes() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			os.write(key.getBytes());
			os.write(passportCreateHeight.getEQCBits());
			if(publickey.isNULL()) {
				os.write(EQCType.NULL);
			}
			else {
				os.write(publickey.getBytes());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error(e.getMessage());
		}
		return os.toByteArray();
	}

	@Override
	public byte[] getHeaderHashBytes(SoleUpdate soleUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBodyHashBytes(SoleUpdate soleUpdate) throws ClassNotFoundException, SQLException, Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			os.write(key.getBytes());
			os.write(passportCreateHeight.getEQCBits());
			soleUpdate.update(os, passportCreateHeight);
			if(publickey.isNULL()) {
				os.write(EQCType.NULL);
			}
			else {
				os.write(publickey.getHashBytes(soleUpdate));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error(e.getMessage());
		}
		return os.toByteArray();
	}

	@Override
	public byte[] getHashBytes(SoleUpdate soleUpdate) throws ClassNotFoundException, SQLException, Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			os.write(getBodyHashBytes(soleUpdate));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error(e.getMessage());
		}
		return os.toByteArray();
	}

//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + Arrays.hashCode(publickey);
//		result = prime * result + ((publickeyCreateHeight == null) ? 0 : publickeyCreateHeight.hashCode());
//		return result;
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Publickey other = (Publickey) obj;
//		if (!Arrays.equals(publickey, other.publickey))
//			return false;
//		if (publickeyCreateHeight == null) {
//			if (other.publickeyCreateHeight != null)
//				return false;
//		} else if (!publickeyCreateHeight.equals(other.publickeyCreateHeight))
//			return false;
//		return true;
//	}
	
}
