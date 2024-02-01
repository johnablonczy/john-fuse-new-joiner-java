package org.galatea.starter.entrypoint;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.ASpringTest;
import org.galatea.starter.domain.IexHistoricalData;
import org.galatea.starter.domain.IexHistoricalDataList;
import org.galatea.starter.domain.rpsy.IHistoricalDataRpsy;
import org.galatea.starter.domain.rpsy.IPriceRpsy;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@RequiredArgsConstructor
@Slf4j
@Category(org.galatea.starter.IntegrationTestCategory.class)
@SpringBootTest
@Ignore
public class IexRestControllerIntegrationTest extends ASpringTest {
  @Value("http://localhost:8080")
  private String FuseHostName;

  @Autowired
  private IPriceRpsy priceRpsy;

  @Autowired
  private IHistoricalDataRpsy historicalDataRpsy;

  @Before
  public void clearDB() {
    priceRpsy.deleteAll();
    historicalDataRpsy.deleteAll();
  }
  @Test
  public void testHistoricalPricesSave() {
    String fuseHostName = System.getProperty("fuse.sandbox.url");
    if (fuseHostName == null || fuseHostName.isEmpty()) {
      fuseHostName = FuseHostName;
    }
    IexRestControllerIntegrationTest.FuseServer fuseServer =
        Feign.builder().decoder(new JacksonDecoder()).encoder(new JacksonEncoder())
            .target(IexRestControllerIntegrationTest.FuseServer.class, fuseHostName);

    List<IexHistoricalData> response = fuseServer.getHistoricalData("aapl", "ytd");

    log.info("Response received from IexRestController: " + response.toString());

    Optional<IexHistoricalDataList> historicalDataFromDB =
        historicalDataRpsy.findById("aapl" + "ytd");

    List<IexHistoricalData> pricesFromDB = historicalDataFromDB.map(
        iexHistoricalDataList -> (List<IexHistoricalData>) priceRpsy.findAllById(
            iexHistoricalDataList.getIds())).orElseGet(ArrayList::new);

    assert(response.size() > 1);
    assert (pricesFromDB.containsAll(response));
  }

  interface FuseServer {

    @RequestLine("GET /iex/historicalPrices?symbol={symbol}&range={range}")
    @Headers("Content-Type: application/json")
    List<IexHistoricalData> getHistoricalData(@Param("symbol") String Symbol,
        @Param("range") String range);
  }


}
