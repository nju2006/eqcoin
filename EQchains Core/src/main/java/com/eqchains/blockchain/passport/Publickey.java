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
import java.util.Arrays;

import com.eqchains.blockchain.accountsmerkletree.PassportsMerkleTree;
import com.eqchains.blockchain.passport.Lock.LockShape;
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
 * Use this class save compressed publickey in Account
 * 
 * @author Xun Wang
 * @date Dec 14, 2018
 * @email 10509759@qq.com
 */
public class Publickey implements EQCHashTypable, EQCHashInheritable {
	private byte[] compressedPublickey;
	private ID publickeyCreateHeight;
	
	public Publickey() {
	}
	
	public Publickey(byte[] bytes) throws NoSuchFieldException, IOException {
		if(EQCType.isNULL(bytes)) {
			return;
		}
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		parseBody(is);
		EQCType.assertNoRedundantData(is);
	}
	
	public Publickey(ByteArrayInputStream is) throws NoSuchFieldException, IOException {
		parseBody(is);
	}
	
	/**
	 * @return the compressedPublickey
	 */
	public byte[] getCompressedPublickey() {
		return compressedPublickey;
	}
	/**
	 * @param compressedPublickey the compressedPublickey to set
	 */
	public void setCompressedPublickey(byte[] compressedPublickey) {
		this.compressedPublickey = compressedPublickey;
	}
	/**
	 * @return the publickeyCreateHeight
	 */
	public ID getPublickeyCreateHeight() {
		return publickeyCreateHeight;
	}
	/**
	 * @param publickeyCreateHeight the publickeyCreateHeight to set
	 */
	public void setPublickeyCreateHeight(ID publickeyCreateHeight) {
		this.publickeyCreateHeight = publickeyCreateHeight;
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
		return "\"Publickey\":" + "{\n" + "\"Publickey\":" + ((compressedPublickey == null)?null:"\"" + Util.dumpBytes(compressedPublickey, 16) + "\"") + ",\n"
				+ "\"PublickeyCreateHeight\":" + "\"" + publickeyCreateHeight + "\""
				+ "\n" + "}";
	}
	
	public static String NULL() {
		return 
		"\"Publickey\":null"; 
	}

	@Override
	public boolean isSanity() {
		if (!isNULL()) {
			if (!publickeyCreateHeight.isSanity()) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(compressedPublickey);
		result = prime * result + ((publickeyCreateHeight == null) ? 0 : publickeyCreateHeight.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publickey other = (Publickey) obj;
		if (!Arrays.equals(compressedPublickey, other.compressedPublickey))
			return false;
		if (publickeyCreateHeight == null) {
			if (other.publickeyCreateHeight != null)
				return false;
		} else if (!publickeyCreateHeight.equals(other.publickeyCreateHeight))
			return false;
		return true;
	}

	public void parseHeader(ByteArrayInputStream is) throws NoSuchFieldException, IOException {
		// TODO Auto-generated method stub
		
	}

	public void parseBody(ByteArrayInputStream is) throws NoSuchFieldException, IOException {
		byte[] data = EQCType.parseBIN(is);
		if(EQCType.isNULL(data)) {
			return;
		}
		else {
		// Parse publickey
		compressedPublickey = data;
		// Parse publickeyCreateHeight
		publickeyCreateHeight = new ID(EQCType.parseEQCBits(is));
		}
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
			os.write(EQCType.bytesToBIN(compressedPublickey));
			os.write(publickeyCreateHeight.getEQCBits());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error(e.getMessage());
		}
		return os.toByteArray();
	}

	public boolean isNULL() {
		return compressedPublickey == null && publickeyCreateHeight == null;
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
			os.write(EQCType.bytesToBIN(compressedPublickey));
			os.write(publickeyCreateHeight.getEQCBits());
			soleUpdate.update(os, publickeyCreateHeight);
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
	
}
