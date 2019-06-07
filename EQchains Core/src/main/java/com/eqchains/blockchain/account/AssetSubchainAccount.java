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
package com.eqchains.blockchain.account;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.acl.Owner;
import java.util.Collections;

import com.eqchains.blockchain.AccountsMerkleTree;
import com.eqchains.blockchain.account.Account.AccountType;
import com.eqchains.blockchain.account.SmartContractAccount.LanguageType;
import com.eqchains.blockchain.transaction.Address.AddressShape;
import com.eqchains.serialization.EQCTypable;
import com.eqchains.serialization.EQCType;
import com.eqchains.serialization.EQCType.ARRAY;
import com.eqchains.util.ID;
import com.eqchains.util.Log;

/**
 * @author Xun Wang
 * @date May 13, 2019
 * @email 10509759@qq.com
 */
public class AssetSubchainAccount extends SmartContractAccount {
	/**
	 * Body field include version and assetSubchainHeader
	 */
	private AssetSubchainHeader assetSubchainHeader;
	public final static byte MAX_VERSION = 0;
	
	public AssetSubchainAccount() {
		super(AccountType.ASSETSUBCHAIN);
		assetSubchainHeader = new AssetSubchainHeader();
	}
	
	public AssetSubchainAccount(byte[] bytes) throws NoSuchFieldException, IOException {
		super(AccountType.ASSETSUBCHAIN);
		EQCType.assertNotNull(bytes);
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		parseHeader(is);
		parseBody(is);
		EQCType.assertNoRedundantData(is);
	}
	
	/* (non-Javadoc)
	 * @see com.eqchains.blockchain.account.SmartContractAccount#parseBody(java.io.ByteArrayInputStream)
	 */
	@Override
	public void parseBody(ByteArrayInputStream is) throws NoSuchFieldException, IOException {
		// TODO Auto-generated method stub
		super.parseBody(is);
		assetSubchainHeader = new AssetSubchainHeader(is);
	}

	@Override
	public byte[] getBodyBytes() {
		// TODO Auto-generated method stub
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			os.write(super.getBodyBytes());
			os.write(assetSubchainHeader.getBodyBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error(e.getMessage());
		}
		return os.toByteArray();
	}

	/**
	 * @return the assetSubchainHeader
	 */
	public AssetSubchainHeader getAssetSubchainHeader() {
		return assetSubchainHeader;
	}

	/**
	 * @param assetSubchainHeader the assetSubchainHeader to set
	 */
	public void setAssetSubchainHeader(AssetSubchainHeader assetSubchainHeader) {
		this.assetSubchainHeader = assetSubchainHeader;
	}
	
	public String toInnerJson() {
		return 
				"\"Account\":" + 
				"\n{\n" +
					"\"AccountType\":" + "\"" + accountType + "\"" + ",\n" +
					"\"Version\":" + "\"" + version + "\"" + ",\n" +
					key.toInnerJson() + ",\n" +
					super.toInnerJson() +
					assetSubchainHeader.toInnerJson() + ",\n" +
					"\"AssetList\":" + "\n{\n" + "\"Size\":" + "\"" + assetList.size() + "\"" + ",\n" + 
					"\"List\":" + "\n" + getAssetListString() + "\n}\n" +
				"}";
	}
	
}