package org.galatea.starter.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Table;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

@Getter
@Table
public class HistoricalPriceEntity {

  @PrimaryKey
  private final String id;

  private final BigDecimal close;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal open;
  private final String symbol;
  private final int volume;
  private final Date date;

  /**
   * JPA entity to store historical price data.
   * @param id id for storage
   * @param close close price
   * @param high high price
   * @param low low price
   * @param open open price
   * @param symbol stock symbol
   * @param volume stock volume
   * @param date date of data
   */
  public HistoricalPriceEntity(final String id, final BigDecimal close, final BigDecimal high,
      final BigDecimal low, final BigDecimal open, final String symbol, final int volume,
      final Date date) {
    this.id = id;
    this.close = close;
    this.high = high;
    this.low = low;
    this.open = open;
    this.symbol = symbol;
    this.volume = volume;
    this.date = date;
  }

}
