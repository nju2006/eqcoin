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
package com.eqchains.transaction;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;

import org.junit.jupiter.api.Test;

/**
 * @author Xun Wang
 * @date Jul 26, 2019
 * @email 10509759@qq.com
 */
public class TransactionTest {
	@Test
	void sendTransaction() {
		UserAccount userAccount = Keystore.getInstance().getUserAccounts().get(1);
		UserAccount userAccount1 = Keystore.getInstance().getUserAccounts().get(3);
		TransferTransaction transaction = new TransferTransaction();
		TxIn txIn = new TxIn();
		txIn.setKey(new Lock(userAccount.getReadableLock()));
		transaction.setTxIn(txIn);
		TxOut txOut = new TxOut();
		txOut.setKey(new Lock(userAccount1.getReadableLock()));
		txOut.setValue(500 * Util.ABC);
		transaction.addTxOut(txOut);
		try {
			transaction.setNonce(Util.DB().getPassport(txIn.getKey().getAddressAI(), Mode.GLOBAL)
					.getAsset(Asset.EQCOIN).getNonce().getNextID());
			byte[] privateKey = Util.AESDecrypt(userAccount.getPrivateKey(), "abc");
			byte[] publickey = Util.AESDecrypt(userAccount.getPublicKey(), "abc");
			CompressedPublickey compressedPublickey = new CompressedPublickey();
			compressedPublickey.setCompressedPublickey(publickey);
			transaction.setCompressedPublickey(compressedPublickey);
			transaction.cypherTxInValue(TXFEE_RATE.POSTPONE0);
			Log.info("getMaxBillingSize: " + transaction.getMaxBillingLength());
			Log.info("getTxFeeLimit: " + transaction.getTxFeeLimit());
			Log.info("getQosRate: " + transaction.getQosRate());
			Log.info("getQos: " + transaction.getQos());

			Signature ecdsa = null;
			try {
				ecdsa = Signature.getInstance("NONEwithECDSA", "SunEC");
				ecdsa.initSign(Util.getPrivateKey(privateKey, transaction.getTxIn().getKey().getAddressType()));
			} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			transaction.sign(ecdsa);
		
			if (transaction.verifySignature()) {
				Log.info("passed");
//				Transaction transaction2 = Transaction.parseRPC(transaction.getRPCBytes());
//				Log.info(Util.dumpBytes(transaction.getSignature(), 16));
//				EQCBlockChainH2.getInstance().saveTransactionInPool(transaction);
				IPList<O> ipList = Util.DB().getMinerList();
				for(String ip:ipList.getIpList()) {
					TransactionNetworkClient.sendTransaction(transaction, ip);
				}
			} else {
				Log.info("failed");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
