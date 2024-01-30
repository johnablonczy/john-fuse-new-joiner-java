package org.galatea.starter.domain.rpsy;

import org.galatea.starter.domain.IexHistoricalData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPriceRpsy extends CrudRepository<IexHistoricalData, Long> {
}
