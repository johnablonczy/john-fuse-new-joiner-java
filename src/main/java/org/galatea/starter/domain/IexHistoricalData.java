package org.galatea.starter.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table("IEX_HISTORICAL_DATA")
public class IexHistoricalData {

  @Column
  @Id
  @PrimaryKey
  private String id;
  @Column
  private BigDecimal close;
  @Column
  private BigDecimal high;
  @Column
  private BigDecimal low;
  @Column
  private BigDecimal open;
  @Column
  private String symbol;
  @Column
  private int volume;
  @Column
  private Date date;
}