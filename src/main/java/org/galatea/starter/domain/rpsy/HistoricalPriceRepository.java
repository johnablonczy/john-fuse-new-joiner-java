package org.galatea.starter.domain.rpsy;

import org.galatea.starter.domain.HistoricalPriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalPriceRepository extends CrudRepository<HistoricalPriceEntity, String> {

}
