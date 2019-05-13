/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 *//**
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
package com.eqchains.rpc.avro;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface TransactionNetwork {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"TransactionNetwork\",\"namespace\":\"com.eqzip.eqcoin.rpc.avro\",\"types\":[{\"type\":\"record\",\"name\":\"Cookie\",\"fields\":[{\"name\":\"ip\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"Status\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"code\",\"type\":\"int\"},{\"name\":\"message\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"MinerList\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"minerList\",\"type\":\"bytes\"}]},{\"type\":\"record\",\"name\":\"TransactionRPC\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"data\",\"type\":\"bytes\"}]}],\"messages\":{\"ping\":{\"request\":[{\"name\":\"cookie\",\"type\":\"Cookie\"}],\"response\":\"Status\"},\"getMinerList\":{\"request\":[{\"name\":\"cookie\",\"type\":\"Cookie\"}],\"response\":\"MinerList\"},\"sendTransaction\":{\"request\":[{\"name\":\"transaction\",\"type\":\"TransactionRPC\"}],\"response\":\"Status\"}}}");
  /**
   */
  com.eqchains.rpc.avro.Status ping(com.eqchains.rpc.avro.Cookie cookie) throws org.apache.avro.AvroRemoteException;
  /**
   */
  com.eqchains.rpc.avro.MinerList getMinerList(com.eqchains.rpc.avro.Cookie cookie) throws org.apache.avro.AvroRemoteException;
  /**
   */
  com.eqchains.rpc.avro.Status sendTransaction(com.eqchains.rpc.avro.TransactionRPC transaction) throws org.apache.avro.AvroRemoteException;

  @SuppressWarnings("all")
  public interface Callback extends TransactionNetwork {
    public static final org.apache.avro.Protocol PROTOCOL = com.eqchains.rpc.avro.TransactionNetwork.PROTOCOL;
    /**
     * @throws java.io.IOException The async call could not be completed.
     */
    void ping(com.eqchains.rpc.avro.Cookie cookie, org.apache.avro.ipc.Callback<com.eqchains.rpc.avro.Status> callback) throws java.io.IOException;
    /**
     * @throws java.io.IOException The async call could not be completed.
     */
    void getMinerList(com.eqchains.rpc.avro.Cookie cookie, org.apache.avro.ipc.Callback<com.eqchains.rpc.avro.MinerList> callback) throws java.io.IOException;
    /**
     * @throws java.io.IOException The async call could not be completed.
     */
    void sendTransaction(com.eqchains.rpc.avro.TransactionRPC transaction, org.apache.avro.ipc.Callback<com.eqchains.rpc.avro.Status> callback) throws java.io.IOException;
  }
}