/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package org.message.queue.kafka.model.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class StockQuoteAvroModel extends org.apache.avro.specific.SpecificRecordBase implements
    org.apache.avro.specific.SpecificRecord {

  private static final long serialVersionUID = 658534804218069373L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
      "{\"type\":\"record\",\"name\":\"StockQuoteAvroModel\",\"namespace\":\"org.message.queue.kafka.model.avro\",\"fields\":[{\"name\":\"symbol\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"exchange\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"currency\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"low\",\"type\":\"double\"},{\"name\":\"high\",\"type\":\"double\"},{\"name\":\"open\",\"type\":\"double\"},{\"name\":\"close\",\"type\":\"double\"},{\"name\":\"volume\",\"type\":\"int\"},{\"name\":\"datetime\",\"type\":\"long\"}]}");

  public static org.apache.avro.Schema getClassSchema() {
    return SCHEMA$;
  }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<StockQuoteAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<StockQuoteAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   *
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<StockQuoteAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   *
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<StockQuoteAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified
   * {@link SchemaStore}.
   *
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<StockQuoteAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this StockQuoteAvroModel to a ByteBuffer.
   *
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a StockQuoteAvroModel from a ByteBuffer.
   *
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a StockQuoteAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of
   *                             this class
   */
  public static StockQuoteAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String symbol;
  private java.lang.String exchange;
  private java.lang.String currency;
  private double low;
  private double high;
  private double open;
  private double close;
  private int volume;
  private long datetime;

  /**
   * Default constructor.  Note that this does not initialize fields to their default values from
   * the schema.  If that is desired then one should use <code>newBuilder()</code>.
   */
  public StockQuoteAvroModel() {
  }

  /**
   * All-args constructor.
   *
   * @param symbol   The new value for symbol
   * @param exchange The new value for exchange
   * @param currency The new value for currency
   * @param low      The new value for low
   * @param high     The new value for high
   * @param open     The new value for open
   * @param close    The new value for close
   * @param volume   The new value for volume
   * @param datetime The new value for datetime
   */
  public StockQuoteAvroModel(java.lang.String symbol, java.lang.String exchange,
      java.lang.String currency, java.lang.Double low, java.lang.Double high, java.lang.Double open,
      java.lang.Double close, java.lang.Integer volume, java.lang.Long datetime) {
    this.symbol = symbol;
    this.exchange = exchange;
    this.currency = currency;
    this.low = low;
    this.high = high;
    this.open = open;
    this.close = close;
    this.volume = volume;
    this.datetime = datetime;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() {
    return MODEL$;
  }

  @Override
  public org.apache.avro.Schema getSchema() {
    return SCHEMA$;
  }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
      case 0:
        return symbol;
      case 1:
        return exchange;
      case 2:
        return currency;
      case 3:
        return low;
      case 4:
        return high;
      case 5:
        return open;
      case 6:
        return close;
      case 7:
        return volume;
      case 8:
        return datetime;
      default:
        throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value = "unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
      case 0:
        symbol = value$ != null ? value$.toString() : null;
        break;
      case 1:
        exchange = value$ != null ? value$.toString() : null;
        break;
      case 2:
        currency = value$ != null ? value$.toString() : null;
        break;
      case 3:
        low = (java.lang.Double) value$;
        break;
      case 4:
        high = (java.lang.Double) value$;
        break;
      case 5:
        open = (java.lang.Double) value$;
        break;
      case 6:
        close = (java.lang.Double) value$;
        break;
      case 7:
        volume = (java.lang.Integer) value$;
        break;
      case 8:
        datetime = (java.lang.Long) value$;
        break;
      default:
        throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'symbol' field.
   *
   * @return The value of the 'symbol' field.
   */
  public java.lang.String getSymbol() {
    return symbol;
  }


  /**
   * Sets the value of the 'symbol' field.
   *
   * @param value the value to set.
   */
  public void setSymbol(java.lang.String value) {
    this.symbol = value;
  }

  /**
   * Gets the value of the 'exchange' field.
   *
   * @return The value of the 'exchange' field.
   */
  public java.lang.String getExchange() {
    return exchange;
  }


  /**
   * Sets the value of the 'exchange' field.
   *
   * @param value the value to set.
   */
  public void setExchange(java.lang.String value) {
    this.exchange = value;
  }

  /**
   * Gets the value of the 'currency' field.
   *
   * @return The value of the 'currency' field.
   */
  public java.lang.String getCurrency() {
    return currency;
  }


  /**
   * Sets the value of the 'currency' field.
   *
   * @param value the value to set.
   */
  public void setCurrency(java.lang.String value) {
    this.currency = value;
  }

  /**
   * Gets the value of the 'low' field.
   *
   * @return The value of the 'low' field.
   */
  public double getLow() {
    return low;
  }


  /**
   * Sets the value of the 'low' field.
   *
   * @param value the value to set.
   */
  public void setLow(double value) {
    this.low = value;
  }

  /**
   * Gets the value of the 'high' field.
   *
   * @return The value of the 'high' field.
   */
  public double getHigh() {
    return high;
  }


  /**
   * Sets the value of the 'high' field.
   *
   * @param value the value to set.
   */
  public void setHigh(double value) {
    this.high = value;
  }

  /**
   * Gets the value of the 'open' field.
   *
   * @return The value of the 'open' field.
   */
  public double getOpen() {
    return open;
  }


  /**
   * Sets the value of the 'open' field.
   *
   * @param value the value to set.
   */
  public void setOpen(double value) {
    this.open = value;
  }

  /**
   * Gets the value of the 'close' field.
   *
   * @return The value of the 'close' field.
   */
  public double getClose() {
    return close;
  }


  /**
   * Sets the value of the 'close' field.
   *
   * @param value the value to set.
   */
  public void setClose(double value) {
    this.close = value;
  }

  /**
   * Gets the value of the 'volume' field.
   *
   * @return The value of the 'volume' field.
   */
  public int getVolume() {
    return volume;
  }


  /**
   * Sets the value of the 'volume' field.
   *
   * @param value the value to set.
   */
  public void setVolume(int value) {
    this.volume = value;
  }

  /**
   * Gets the value of the 'datetime' field.
   *
   * @return The value of the 'datetime' field.
   */
  public long getDatetime() {
    return datetime;
  }


  /**
   * Sets the value of the 'datetime' field.
   *
   * @param value the value to set.
   */
  public void setDatetime(long value) {
    this.datetime = value;
  }

  /**
   * Creates a new StockQuoteAvroModel RecordBuilder.
   *
   * @return A new StockQuoteAvroModel RecordBuilder
   */
  public static org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder newBuilder() {
    return new org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder();
  }

  /**
   * Creates a new StockQuoteAvroModel RecordBuilder by copying an existing Builder.
   *
   * @param other The existing builder to copy.
   * @return A new StockQuoteAvroModel RecordBuilder
   */
  public static org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder newBuilder(
      org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder other) {
    if (other == null) {
      return new org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder();
    } else {
      return new org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new StockQuoteAvroModel RecordBuilder by copying an existing StockQuoteAvroModel
   * instance.
   *
   * @param other The existing instance to copy.
   * @return A new StockQuoteAvroModel RecordBuilder
   */
  public static org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder newBuilder(
      org.message.queue.kafka.model.avro.StockQuoteAvroModel other) {
    if (other == null) {
      return new org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder();
    } else {
      return new org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for StockQuoteAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends
      org.apache.avro.specific.SpecificRecordBuilderBase<StockQuoteAvroModel>
      implements org.apache.avro.data.RecordBuilder<StockQuoteAvroModel> {

    private java.lang.String symbol;
    private java.lang.String exchange;
    private java.lang.String currency;
    private double low;
    private double high;
    private double open;
    private double close;
    private int volume;
    private long datetime;

    /**
     * Creates a new Builder
     */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     *
     * @param other The existing Builder to copy.
     */
    private Builder(org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.symbol)) {
        this.symbol = data().deepCopy(fields()[0].schema(), other.symbol);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.exchange)) {
        this.exchange = data().deepCopy(fields()[1].schema(), other.exchange);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.currency)) {
        this.currency = data().deepCopy(fields()[2].schema(), other.currency);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.low)) {
        this.low = data().deepCopy(fields()[3].schema(), other.low);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.high)) {
        this.high = data().deepCopy(fields()[4].schema(), other.high);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.open)) {
        this.open = data().deepCopy(fields()[5].schema(), other.open);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.close)) {
        this.close = data().deepCopy(fields()[6].schema(), other.close);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.volume)) {
        this.volume = data().deepCopy(fields()[7].schema(), other.volume);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
      if (isValidValue(fields()[8], other.datetime)) {
        this.datetime = data().deepCopy(fields()[8].schema(), other.datetime);
        fieldSetFlags()[8] = other.fieldSetFlags()[8];
      }
    }

    /**
     * Creates a Builder by copying an existing StockQuoteAvroModel instance
     *
     * @param other The existing instance to copy.
     */
    private Builder(org.message.queue.kafka.model.avro.StockQuoteAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.symbol)) {
        this.symbol = data().deepCopy(fields()[0].schema(), other.symbol);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.exchange)) {
        this.exchange = data().deepCopy(fields()[1].schema(), other.exchange);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.currency)) {
        this.currency = data().deepCopy(fields()[2].schema(), other.currency);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.low)) {
        this.low = data().deepCopy(fields()[3].schema(), other.low);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.high)) {
        this.high = data().deepCopy(fields()[4].schema(), other.high);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.open)) {
        this.open = data().deepCopy(fields()[5].schema(), other.open);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.close)) {
        this.close = data().deepCopy(fields()[6].schema(), other.close);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.volume)) {
        this.volume = data().deepCopy(fields()[7].schema(), other.volume);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.datetime)) {
        this.datetime = data().deepCopy(fields()[8].schema(), other.datetime);
        fieldSetFlags()[8] = true;
      }
    }

    /**
     * Gets the value of the 'symbol' field.
     *
     * @return The value.
     */
    public java.lang.String getSymbol() {
      return symbol;
    }


    /**
     * Sets the value of the 'symbol' field.
     *
     * @param value The value of 'symbol'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setSymbol(
        java.lang.String value) {
      validate(fields()[0], value);
      this.symbol = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
     * Checks whether the 'symbol' field has been set.
     *
     * @return True if the 'symbol' field has been set, false otherwise.
     */
    public boolean hasSymbol() {
      return fieldSetFlags()[0];
    }


    /**
     * Clears the value of the 'symbol' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearSymbol() {
      symbol = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
     * Gets the value of the 'exchange' field.
     *
     * @return The value.
     */
    public java.lang.String getExchange() {
      return exchange;
    }


    /**
     * Sets the value of the 'exchange' field.
     *
     * @param value The value of 'exchange'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setExchange(
        java.lang.String value) {
      validate(fields()[1], value);
      this.exchange = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
     * Checks whether the 'exchange' field has been set.
     *
     * @return True if the 'exchange' field has been set, false otherwise.
     */
    public boolean hasExchange() {
      return fieldSetFlags()[1];
    }


    /**
     * Clears the value of the 'exchange' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearExchange() {
      exchange = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
     * Gets the value of the 'currency' field.
     *
     * @return The value.
     */
    public java.lang.String getCurrency() {
      return currency;
    }


    /**
     * Sets the value of the 'currency' field.
     *
     * @param value The value of 'currency'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setCurrency(
        java.lang.String value) {
      validate(fields()[2], value);
      this.currency = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
     * Checks whether the 'currency' field has been set.
     *
     * @return True if the 'currency' field has been set, false otherwise.
     */
    public boolean hasCurrency() {
      return fieldSetFlags()[2];
    }


    /**
     * Clears the value of the 'currency' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearCurrency() {
      currency = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
     * Gets the value of the 'low' field.
     *
     * @return The value.
     */
    public double getLow() {
      return low;
    }


    /**
     * Sets the value of the 'low' field.
     *
     * @param value The value of 'low'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setLow(double value) {
      validate(fields()[3], value);
      this.low = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
     * Checks whether the 'low' field has been set.
     *
     * @return True if the 'low' field has been set, false otherwise.
     */
    public boolean hasLow() {
      return fieldSetFlags()[3];
    }


    /**
     * Clears the value of the 'low' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearLow() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
     * Gets the value of the 'high' field.
     *
     * @return The value.
     */
    public double getHigh() {
      return high;
    }


    /**
     * Sets the value of the 'high' field.
     *
     * @param value The value of 'high'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setHigh(double value) {
      validate(fields()[4], value);
      this.high = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
     * Checks whether the 'high' field has been set.
     *
     * @return True if the 'high' field has been set, false otherwise.
     */
    public boolean hasHigh() {
      return fieldSetFlags()[4];
    }


    /**
     * Clears the value of the 'high' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearHigh() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
     * Gets the value of the 'open' field.
     *
     * @return The value.
     */
    public double getOpen() {
      return open;
    }


    /**
     * Sets the value of the 'open' field.
     *
     * @param value The value of 'open'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setOpen(double value) {
      validate(fields()[5], value);
      this.open = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
     * Checks whether the 'open' field has been set.
     *
     * @return True if the 'open' field has been set, false otherwise.
     */
    public boolean hasOpen() {
      return fieldSetFlags()[5];
    }


    /**
     * Clears the value of the 'open' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearOpen() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
     * Gets the value of the 'close' field.
     *
     * @return The value.
     */
    public double getClose() {
      return close;
    }


    /**
     * Sets the value of the 'close' field.
     *
     * @param value The value of 'close'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setClose(double value) {
      validate(fields()[6], value);
      this.close = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
     * Checks whether the 'close' field has been set.
     *
     * @return True if the 'close' field has been set, false otherwise.
     */
    public boolean hasClose() {
      return fieldSetFlags()[6];
    }


    /**
     * Clears the value of the 'close' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearClose() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
     * Gets the value of the 'volume' field.
     *
     * @return The value.
     */
    public int getVolume() {
      return volume;
    }


    /**
     * Sets the value of the 'volume' field.
     *
     * @param value The value of 'volume'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setVolume(int value) {
      validate(fields()[7], value);
      this.volume = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
     * Checks whether the 'volume' field has been set.
     *
     * @return True if the 'volume' field has been set, false otherwise.
     */
    public boolean hasVolume() {
      return fieldSetFlags()[7];
    }


    /**
     * Clears the value of the 'volume' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearVolume() {
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
     * Gets the value of the 'datetime' field.
     *
     * @return The value.
     */
    public long getDatetime() {
      return datetime;
    }


    /**
     * Sets the value of the 'datetime' field.
     *
     * @param value The value of 'datetime'.
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder setDatetime(long value) {
      validate(fields()[8], value);
      this.datetime = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
     * Checks whether the 'datetime' field has been set.
     *
     * @return True if the 'datetime' field has been set, false otherwise.
     */
    public boolean hasDatetime() {
      return fieldSetFlags()[8];
    }


    /**
     * Clears the value of the 'datetime' field.
     *
     * @return This builder.
     */
    public org.message.queue.kafka.model.avro.StockQuoteAvroModel.Builder clearDatetime() {
      fieldSetFlags()[8] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public StockQuoteAvroModel build() {
      try {
        StockQuoteAvroModel record = new StockQuoteAvroModel();
        record.symbol =
            fieldSetFlags()[0] ? this.symbol : (java.lang.String) defaultValue(fields()[0]);
        record.exchange =
            fieldSetFlags()[1] ? this.exchange : (java.lang.String) defaultValue(fields()[1]);
        record.currency =
            fieldSetFlags()[2] ? this.currency : (java.lang.String) defaultValue(fields()[2]);
        record.low = fieldSetFlags()[3] ? this.low : (java.lang.Double) defaultValue(fields()[3]);
        record.high = fieldSetFlags()[4] ? this.high : (java.lang.Double) defaultValue(fields()[4]);
        record.open = fieldSetFlags()[5] ? this.open : (java.lang.Double) defaultValue(fields()[5]);
        record.close =
            fieldSetFlags()[6] ? this.close : (java.lang.Double) defaultValue(fields()[6]);
        record.volume =
            fieldSetFlags()[7] ? this.volume : (java.lang.Integer) defaultValue(fields()[7]);
        record.datetime =
            fieldSetFlags()[8] ? this.datetime : (java.lang.Long) defaultValue(fields()[8]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<StockQuoteAvroModel>
      WRITER$ = (org.apache.avro.io.DatumWriter<StockQuoteAvroModel>) MODEL$.createDatumWriter(
      SCHEMA$);

  @Override
  public void writeExternal(java.io.ObjectOutput out)
      throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<StockQuoteAvroModel>
      READER$ = (org.apache.avro.io.DatumReader<StockQuoteAvroModel>) MODEL$.createDatumReader(
      SCHEMA$);

  @Override
  public void readExternal(java.io.ObjectInput in)
      throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override
  protected boolean hasCustomCoders() {
    return true;
  }

  @Override
  public void customEncode(org.apache.avro.io.Encoder out)
      throws java.io.IOException {
    out.writeString(this.symbol);

    out.writeString(this.exchange);

    out.writeString(this.currency);

    out.writeDouble(this.low);

    out.writeDouble(this.high);

    out.writeDouble(this.open);

    out.writeDouble(this.close);

    out.writeInt(this.volume);

    out.writeLong(this.datetime);

  }

  @Override
  public void customDecode(org.apache.avro.io.ResolvingDecoder in)
      throws java.io.IOException {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.symbol = in.readString();

      this.exchange = in.readString();

      this.currency = in.readString();

      this.low = in.readDouble();

      this.high = in.readDouble();

      this.open = in.readDouble();

      this.close = in.readDouble();

      this.volume = in.readInt();

      this.datetime = in.readLong();

    } else {
      for (int i = 0; i < 9; i++) {
        switch (fieldOrder[i].pos()) {
          case 0:
            this.symbol = in.readString();
            break;

          case 1:
            this.exchange = in.readString();
            break;

          case 2:
            this.currency = in.readString();
            break;

          case 3:
            this.low = in.readDouble();
            break;

          case 4:
            this.high = in.readDouble();
            break;

          case 5:
            this.open = in.readDouble();
            break;

          case 6:
            this.close = in.readDouble();
            break;

          case 7:
            this.volume = in.readInt();
            break;

          case 8:
            this.datetime = in.readLong();
            break;

          default:
            throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










