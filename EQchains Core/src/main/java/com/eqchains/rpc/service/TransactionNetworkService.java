/**
 * EQchains core - EQchains Federation's EQchains core library
 * @copyright 2018-present EQchains Federation All rights reserved...
 * Copyright of all works released by EQchains Federation or jointly released by
 * EQchains Federation with cooperative partners are owned by EQchains Federation
 * and entitled to protection available from copyright law by country as well as
 * international conventions.
 * Attribution — You must give appropriate credit, provide a link to the license.
 * Non Commercial — You may not use the material for commercial purposes.
 * No Derivatives — If you remix, transform, or build upon the material, you may
 * not distribute the modified material.
 * For any use of above stated content of copyright beyond the scope of fair use
 * or without prior written permission, EQchains Federation reserves all rights to
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
package com.eqchains.rpc.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.netty.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import com.eqchains.avro.O;
import com.eqchains.avro.SyncblockNetwork;
import com.eqchains.avro.TransactionNetwork;
import com.eqchains.keystore.Keystore;
import com.eqchains.util.Log;
import com.eqchains.util.Util;

/**
 * @author Xun Wang
 * @date Jan 24, 2019
 * @email 10509759@qq.com
 */
public class TransactionNetworkService extends EQCRPCService {
	private static TransactionNetworkService instance;
	
	private TransactionNetworkService() {
	}
	
	public static TransactionNetworkService getInstance() {
		if (instance == null) {
			synchronized (TransactionNetworkService.class) {
				if (instance == null) {
					instance = new TransactionNetworkService();
				}
			}
		}
		return (TransactionNetworkService) instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#start()
	 */
	@Override
	public synchronized void start() {
		super.start();
		server = new NettyServer(new SpecificResponder(TransactionNetwork.class, new TransactionNetworkServiceImpl()),
				new InetSocketAddress(Util.TRANSACTION_NETWORK_PORT));
		Log.info(this.getClass().getSimpleName() + " started...");
	}

}
