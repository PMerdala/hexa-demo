package pl.merdala.hexademo.api;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.merdala.hexademo.domain.DomainModelFaker;
import pl.merdala.hexademo.domain.service.StockPosition;
import pl.merdala.hexademo.domain.service.StockPositionsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Tag("E2E")
public class GetStockPositionAndMarketValueApiE2ETest {

    @Autowired
    private ApplicationContext context;


    @Autowired
    StockPositionsRepository stockPositionsRepository;
    public static final String USER = "Pawel";


    @Test
    @WithMockUser(USER)
    void getStockPositionAndMarketValue() {
        //arrange
        WebTestClient client = WebTestClient.bindToApplicationContext(context).build();
        String symbol = DomainModelFaker.fakeStockSymbol();
        StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(USER, symbol);
        //seed database
        stockPositionsRepository
                .deleteAll()
                .then(stockPositionsRepository.insert(fakeStockPosition))
                .block();
        //act
        client.get().uri("/stock-position-market-value/" + symbol)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // assert
                .expectStatus().isOk()
                .expectBody(GetStockPositionAndMarketValueApitResponseDto.class)
                .value(dto -> assertAll(
                        () -> assertThat(dto.getSymbol()).isEqualTo(symbol),
                        () -> assertThat(dto.getQuantity().doubleValue())
                                .isCloseTo(fakeStockPosition.getQuantity().doubleValue(), Offset.offset(0.01)),
                        () -> assertThat(dto.getCurrencyCode()).isEqualTo(fakeStockPosition.getCurrencyCode()),
                        () -> assertThat(dto.getCost().doubleValue())
                                .isCloseTo(fakeStockPosition.getCost().doubleValue(), Offset.offset(0.0001)),
                        () -> assertThat(dto.getMarketValue().doubleValue())
                                .isGreaterThan(0d)
                        )
                );
    }

}
