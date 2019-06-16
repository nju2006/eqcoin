/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.eqchains.rpc.avro;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface SyncblockNetwork {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"SyncblockNetwork\",\"namespace\":\"com.eqchains.rpc.avro\",\"types\":[{\"type\":\"record\",\"name\":\"Cookie\",\"fields\":[{\"name\":\"ip\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"Status\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"code\",\"type\":\"int\"},{\"name\":\"message\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"MinerList\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"minerList\",\"type\":\"bytes\"}]},{\"type\":\"record\",\"name\":\"FullNodeList\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"fullNodeList\",\"type\":\"bytes\"}]},{\"type\":\"record\",\"name\":\"Height\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"height\",\"type\":\"long\"}]},{\"type\":\"record\",\"name\":\"Europa\",\"fields\":[{\"name\":\"height\",\"type\":\"Height\"},{\"name\":\"nonce\",\"type\":\"long\"}]},{\"type\":\"record\",\"name\":\"Block\",\"fields\":[{\"name\":\"cookie\",\"type\":\"Cookie\"},{\"name\":\"block\",\"type\":\"bytes\"}]}],\"messages\":{\"ping\":{\"request\":[{\"name\":\"cookie\",\"type\":\"Cookie\"}],\"response\":\"Status\"},\"getMinerList\":{\"request\":[{\"name\":\"cookie\",\"type\":\"Cookie\"}],\"response\":\"MinerList\"},\"getFullNodeList\":{\"request\":[{\"name\":\"cookie\",\"type\":\"Cookie\"}],\"response\":\"FullNodeList\"},\"getBlockTail\":{\"request\":[{\"name\":\"cookie\",\"type\":\"Cookie\"}],\"response\":\"Europa\"},\"getBlock\":{\"request\":[{\"name\":\"height\",\"type\":\"Height\"}],\"response\":\"Block\"}}}");
  /**
   */
  com.eqchains.rpc.avro.Status ping(com.eqchains.rpc.avro.Cookie cookie) throws org.apache.avro.AvroRemoteException;
  /**
   */
  com.eqchains.rpc.avro.MinerList getMinerList(com.eqchains.rpc.avro.Cookie cookie) throws org.apache.avro.AvroRemoteException;
  /**
   */
  com.eqchains.rpc.avro.FullNodeList getFullNodeList(com.eqchains.rpc.avro.Cookie cookie) throws org.apache.avro.AvroRemoteException;
  /**
   */
  com.eqchains.rpc.avro.Europa getBlockTail(com.eqchains.rpc.avro.Cookie cookie) throws org.apache.avro.AvroRemoteException;
  /**
   */
  com.eqchains.rpc.avro.Block getBlock(com.eqchains.rpc.avro.Height height) throws org.apache.avro.AvroRemoteException;

  @SuppressWarnings("all")
  public interface Callback extends SyncblockNetwork {
    public static final org.apache.avro.Protocol PROTOCOL = com.eqchains.rpc.avro.SyncblockNetwork.PROTOCOL;
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
    void getFullNodeList(com.eqchains.rpc.avro.Cookie cookie, org.apache.avro.ipc.Callback<com.eqchains.rpc.avro.FullNodeList> callback) throws java.io.IOException;
    /**
     * @throws java.io.IOException The async call could not be completed.
     */
    void getBlockTail(com.eqchains.rpc.avro.Cookie cookie, org.apache.avro.ipc.Callback<com.eqchains.rpc.avro.Europa> callback) throws java.io.IOException;
    /**
     * @throws java.io.IOException The async call could not be completed.
     */
    void getBlock(com.eqchains.rpc.avro.Height height, org.apache.avro.ipc.Callback<com.eqchains.rpc.avro.Block> callback) throws java.io.IOException;
  }
}