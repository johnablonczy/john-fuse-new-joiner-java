package org.galatea.starter.domain;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("IEX_HISTORICAL_DATA_LIST")
public class IexHistoricalDataList {

  @Id
  @PrimaryKey
  @Column
  protected String path;

  @NonNull
  @ElementCollection
  @Column
  protected List<String> ids;

}