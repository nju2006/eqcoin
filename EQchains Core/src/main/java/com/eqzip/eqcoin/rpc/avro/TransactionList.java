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
package com.eqzip.eqcoin.rpc.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TransactionList extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1151172801088335867L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TransactionList\",\"namespace\":\"com.eqzip.eqcoin.rpc.avro\",\"fields\":[{\"name\":\"cookie\",\"type\":{\"type\":\"record\",\"name\":\"Cookie\",\"fields\":[{\"name\":\"ip\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"}]}},{\"name\":\"transactionList\",\"type\":\"bytes\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TransactionList> ENCODER =
      new BinaryMessageEncoder<TransactionList>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TransactionList> DECODER =
      new BinaryMessageDecoder<TransactionList>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<TransactionList> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<TransactionList> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TransactionList>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this TransactionList to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a TransactionList from a ByteBuffer. */
  public static TransactionList fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public com.eqzip.eqcoin.rpc.avro.Cookie cookie;
  @Deprecated public java.nio.ByteBuffer transactionList;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TransactionList() {}

  /**
   * All-args constructor.
   * @param cookie The new value for cookie
   * @param transactionList The new value for transactionList
   */
  public TransactionList(com.eqzip.eqcoin.rpc.avro.Cookie cookie, java.nio.ByteBuffer transactionList) {
    this.cookie = cookie;
    this.transactionList = transactionList;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return cookie;
    case 1: return transactionList;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: cookie = (com.eqzip.eqcoin.rpc.avro.Cookie)value$; break;
    case 1: transactionList = (java.nio.ByteBuffer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'cookie' field.
   * @return The value of the 'cookie' field.
   */
  public com.eqzip.eqcoin.rpc.avro.Cookie getCookie() {
    return cookie;
  }

  /**
   * Sets the value of the 'cookie' field.
   * @param value the value to set.
   */
  public void setCookie(com.eqzip.eqcoin.rpc.avro.Cookie value) {
    this.cookie = value;
  }

  /**
   * Gets the value of the 'transactionList' field.
   * @return The value of the 'transactionList' field.
   */
  public java.nio.ByteBuffer getTransactionList() {
    return transactionList;
  }

  /**
   * Sets the value of the 'transactionList' field.
   * @param value the value to set.
   */
  public void setTransactionList(java.nio.ByteBuffer value) {
    this.transactionList = value;
  }

  /**
   * Creates a new TransactionList RecordBuilder.
   * @return A new TransactionList RecordBuilder
   */
  public static com.eqzip.eqcoin.rpc.avro.TransactionList.Builder newBuilder() {
    return new com.eqzip.eqcoin.rpc.avro.TransactionList.Builder();
  }

  /**
   * Creates a new TransactionList RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TransactionList RecordBuilder
   */
  public static com.eqzip.eqcoin.rpc.avro.TransactionList.Builder newBuilder(com.eqzip.eqcoin.rpc.avro.TransactionList.Builder other) {
    return new com.eqzip.eqcoin.rpc.avro.TransactionList.Builder(other);
  }

  /**
   * Creates a new TransactionList RecordBuilder by copying an existing TransactionList instance.
   * @param other The existing instance to copy.
   * @return A new TransactionList RecordBuilder
   */
  public static com.eqzip.eqcoin.rpc.avro.TransactionList.Builder newBuilder(com.eqzip.eqcoin.rpc.avro.TransactionList other) {
    return new com.eqzip.eqcoin.rpc.avro.TransactionList.Builder(other);
  }

  /**
   * RecordBuilder for TransactionList instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TransactionList>
    implements org.apache.avro.data.RecordBuilder<TransactionList> {

    private com.eqzip.eqcoin.rpc.avro.Cookie cookie;
    private com.eqzip.eqcoin.rpc.avro.Cookie.Builder cookieBuilder;
    private java.nio.ByteBuffer transactionList;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.eqzip.eqcoin.rpc.avro.TransactionList.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.cookie)) {
        this.cookie = data().deepCopy(fields()[0].schema(), other.cookie);
        fieldSetFlags()[0] = true;
      }
      if (other.hasCookieBuilder()) {
        this.cookieBuilder = com.eqzip.eqcoin.rpc.avro.Cookie.newBuilder(other.getCookieBuilder());
      }
      if (isValidValue(fields()[1], other.transactionList)) {
        this.transactionList = data().deepCopy(fields()[1].schema(), other.transactionList);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TransactionList instance
     * @param other The existing instance to copy.
     */
    private Builder(com.eqzip.eqcoin.rpc.avro.TransactionList other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.cookie)) {
        this.cookie = data().deepCopy(fields()[0].schema(), other.cookie);
        fieldSetFlags()[0] = true;
      }
      this.cookieBuilder = null;
      if (isValidValue(fields()[1], other.transactionList)) {
        this.transactionList = data().deepCopy(fields()[1].schema(), other.transactionList);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'cookie' field.
      * @return The value.
      */
    public com.eqzip.eqcoin.rpc.avro.Cookie getCookie() {
      return cookie;
    }

    /**
      * Sets the value of the 'cookie' field.
      * @param value The value of 'cookie'.
      * @return This builder.
      */
    public com.eqzip.eqcoin.rpc.avro.TransactionList.Builder setCookie(com.eqzip.eqcoin.rpc.avro.Cookie value) {
      validate(fields()[0], value);
      this.cookieBuilder = null;
      this.cookie = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'cookie' field has been set.
      * @return True if the 'cookie' field has been set, false otherwise.
      */
    public boolean hasCookie() {
      return fieldSetFlags()[0];
    }

    /**
     * Gets the Builder instance for the 'cookie' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.eqzip.eqcoin.rpc.avro.Cookie.Builder getCookieBuilder() {
      if (cookieBuilder == null) {
        if (hasCookie()) {
          setCookieBuilder(com.eqzip.eqcoin.rpc.avro.Cookie.newBuilder(cookie));
        } else {
          setCookieBuilder(com.eqzip.eqcoin.rpc.avro.Cookie.newBuilder());
        }
      }
      return cookieBuilder;
    }

    /**
     * Sets the Builder instance for the 'cookie' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.eqzip.eqcoin.rpc.avro.TransactionList.Builder setCookieBuilder(com.eqzip.eqcoin.rpc.avro.Cookie.Builder value) {
      clearCookie();
      cookieBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'cookie' field has an active Builder instance
     * @return True if the 'cookie' field has an active Builder instance
     */
    public boolean hasCookieBuilder() {
      return cookieBuilder != null;
    }

    /**
      * Clears the value of the 'cookie' field.
      * @return This builder.
      */
    public com.eqzip.eqcoin.rpc.avro.TransactionList.Builder clearCookie() {
      cookie = null;
      cookieBuilder = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionList' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getTransactionList() {
      return transactionList;
    }

    /**
      * Sets the value of the 'transactionList' field.
      * @param value The value of 'transactionList'.
      * @return This builder.
      */
    public com.eqzip.eqcoin.rpc.avro.TransactionList.Builder setTransactionList(java.nio.ByteBuffer value) {
      validate(fields()[1], value);
      this.transactionList = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionList' field has been set.
      * @return True if the 'transactionList' field has been set, false otherwise.
      */
    public boolean hasTransactionList() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'transactionList' field.
      * @return This builder.
      */
    public com.eqzip.eqcoin.rpc.avro.TransactionList.Builder clearTransactionList() {
      transactionList = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransactionList build() {
      try {
        TransactionList record = new TransactionList();
        if (cookieBuilder != null) {
          record.cookie = this.cookieBuilder.build();
        } else {
          record.cookie = fieldSetFlags()[0] ? this.cookie : (com.eqzip.eqcoin.rpc.avro.Cookie) defaultValue(fields()[0]);
        }
        record.transactionList = fieldSetFlags()[1] ? this.transactionList : (java.nio.ByteBuffer) defaultValue(fields()[1]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TransactionList>
    WRITER$ = (org.apache.avro.io.DatumWriter<TransactionList>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TransactionList>
    READER$ = (org.apache.avro.io.DatumReader<TransactionList>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
