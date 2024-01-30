package org.galatea.starter.entrypoint;

import static org.junit.Assert.assertThat;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.ASpringTest;
import org.galatea.starter.domain.IexHistoricalData;
import org.galatea.starter.domain.rpsy.IHistoricalDataRpsy;
import org.galatea.starter.domain.rpsy.IPriceRpsy;
import org.galatea.starter.entrypoint.HalRestControllerIntegrationTest.FuseServer;
import org.galatea.starter.entrypoint.messagecontracts.ProtobufMessages.SettlementMissionProtoMessage;
import org.galatea.starter.entrypoint.messagecontracts.ProtobufMessages.SettlementResponseProtoMessage;
import org.galatea.starter.entrypoint.messagecontracts.ProtobufMessages.TradeAgreementProtoMessages;
import org.galatea.starter.entrypoint.messagecontracts.SettlementMissionList;
import org.galatea.starter.entrypoint.messagecontracts.SettlementMissionMessage;
import org.galatea.starter.entrypoint.messagecontracts.SettlementResponseMessage;
import org.galatea.starter.entrypoint.messagecontracts.TradeAgreementMessages;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Slf4j
@Category(org.galatea.starter.IntegrationTestCategory.class)
@SpringBootTest
@Ignore
public class IexRestControllerIntegrationTest extends ASpringTest {
  @Value("${fuse-host.url}")
  private String FuseHostName;

  @Autowired
  private IPriceRpsy priceRpsy;

  @Autowired
  private IHistoricalDataRpsy historicalDataRpsy;
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

    assert(response.size() > 1);
  }

  interface FuseServer {

    @RequestLine("GET /iex/historicalPrices?symbol={symbol}&range={range}")
    @Headers("Content-Type: application/json")
    List<IexHistoricalData> getHistoricalData(@RequestParam("symbol") String Symbol,
        @RequestParam("range") String range);
  }


}
