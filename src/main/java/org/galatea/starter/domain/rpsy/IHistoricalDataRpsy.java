package org.galatea.starter.domain.rpsy;

import org.galatea.starter.domain.IexHistoricalDataList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistoricalDataRpsy extends CrudRepository<IexHistoricalDataList, String> {
}