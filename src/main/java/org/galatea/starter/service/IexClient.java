package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalData;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEX", url = "${spring.rest.iexBasePath}")
public interface IexClient {

  /**
   * Get a list of all stocks supported by IEX. See https://iextrading.com/developer/docs/#symbols.
   * As of July 2019 this returns almost 9,000 symbols, so maybe don't call it in a loop.
   *
   * @return a list of all of the stock symbols supported by IEX.
   */
  @GetMapping("/ref-data/symbols?token=sk_a94ce7dbb393419495e948c5602bf777")
  List<IexSymbol> getAllSymbols();

  /**
   * Get the last traded price for each stock symbol passed in. See
   * https://iextrading.com/developer/docs/#last.
   *
   * @param symbols stock symbols to get last traded price for.
   * @return a list of the last traded price for each of the symbols passed in.
   */
  @GetMapping("/tops/last?token=sk_a94ce7dbb393419495e948c5602bf777")
  List<IexLastTradedPrice> getLastTradedPriceForSymbols(@RequestParam("symbols") String[] symbols);

  /**
   * Get the adjusted and unadjusted data for up to 15 years, and historical minute-by-minute
   * intraday prices for the last 30 trailing calendar days.
   *
   * @param symbol stock symbol to get historical data for.
   * @param range range of time to get data for. (2y, 5y, 1y, ytd, 6m ...)
   * @return a JSON object with data pertaining to the symbol within the specified time range.
   */

  @GetMapping(
      "/stock/{symbol}/chart/{range}?token=sk_a94ce7dbb393419495e948c5602bf777"
          + "&filter=close,high,low,open,symbol,volume,date")
  List<IexHistoricalData> getHistoricalDataForSymbolAndRange(@PathVariable("symbol") String symbol,
      @PathVariable("range") String range);
}
