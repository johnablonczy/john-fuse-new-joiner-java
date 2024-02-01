package org.galatea.starter.service;

import feign.FeignException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.IexHistoricalData;
import org.galatea.starter.domain.IexHistoricalDataList;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.galatea.starter.domain.rpsy.IHistoricalDataRpsy;
import org.galatea.starter.domain.rpsy.IPriceRpsy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from IEX.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IexService {

  @NonNull
  private IexClient iexClient;

  @Autowired
  private IHistoricalDataRpsy historicalDataRpsy;

  @Autowired
  private IPriceRpsy priceRpsy;


  /**
   * Get all stock symbols from IEX.
   *
   * @return a list of all Stock Symbols from IEX.
   */
  public List<IexSymbol> getAllSymbols() {
    return iexClient.getAllSymbols();
  }

  /**
   * Get the last traded price for each Symbol that is passed in.
   *
   * @param symbols the list of symbols to get a last traded price for.
   * @return a list of last traded price objects for each Symbol that is passed in.
   */
  public List<IexLastTradedPrice> getLastTradedPriceForSymbols(final List<String> symbols) {
    if (CollectionUtils.isEmpty(symbols)) {
      return Collections.emptyList();
    } else {
      return iexClient.getLastTradedPriceForSymbols(symbols.toArray(new String[0]));
    }
  }

  /**
   * Get the adjusted and unadjusted data for up to 15 years, and historical minute-by-minute
   * intraday prices for the last 30 trailing calendar days.
   *
   * @param symbol stock symbol to get historical data for.
   * @param range range of time to get data for. (2y, 5y, 1y, ytd, 6m ...)
   * @return a JSON object with data pertaining to the symbol within the specified time range.
   */
  public List<IexHistoricalData> getHistoricalDataForSymbolAndRange(final String symbol,
      final String range) {
    if (symbol.isEmpty() || range.isEmpty()) {
      return Collections.emptyList();
    }

    /*
    * Try catch to gracefully let the user know something went wrong with the request, avoiding
    * the white label error page. This will be hit when the symbol or range is not recognized by
    * Iex.
    * */

    try {
      List<IexHistoricalData> prices = new ArrayList<>();
      for(int i = 0; i < 10; i++) {
        prices.add(IexHistoricalData.builder().symbol("aapl").build());
      }
      IexHistoricalDataList list = IexHistoricalDataList.builder()
          .path(symbol)
          .ids(prices.stream().map(IexHistoricalData::getId).collect(Collectors.toList()))
          .build();
      priceRpsy.saveAll(prices);
      historicalDataRpsy.save(list);
      return prices;
    } catch (FeignException e) {
      return Collections.singletonList(IexHistoricalData.builder()
          .symbol("Request error")
          .build());
    }
  }

}
