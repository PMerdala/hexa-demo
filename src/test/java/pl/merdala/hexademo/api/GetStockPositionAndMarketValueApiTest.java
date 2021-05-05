package pl.merdala.hexademo.api;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.merdala.hexademo.domain.DomainModelFaker;
import pl.merdala.hexademo.domain.service.GetStockMarketValueService;
import pl.merdala.hexademo.domain.service.GetStockPositionService;
import pl.merdala.hexademo.domain.service.StockPosition;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@WebFluxTest
@Tag("Spring")
public class GetStockPositionAndMarketValueApiTest {

    @Autowired
    WebTestClient client;


    private final static String symbol = "aapl";
    private final static String user = "pawel";

    @MockBean
    private GetStockPositionService getStockPositionService;

    @MockBean
    private GetStockMarketValueService getStockMarketValueService;

    @Test
    @WithMockUser(user)
    void get() {
        StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(user, symbol);
        when(getStockPositionService.get(user, symbol)).thenReturn(Mono.just(fakeStockPosition));
        BigDecimal fakeMarketPrice = BigDecimal.valueOf(DomainModelFaker.faker.number()
                .randomDouble(4, 0, 1_000_000));
        when(getStockMarketValueService.get(symbol, fakeStockPosition.getQuantity())).thenReturn(Mono.just((fakeMarketPrice)));
        makeGetRequest(symbol)
                //assert
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
                                .isCloseTo(fakeMarketPrice.doubleValue(), Offset.offset(0.0001))
                ));
    }

    @Test
    @WithAnonymousUser
    void anonymousGet() {
        makeGetRequest(symbol)
                .expectStatus().isForbidden();
    }

    @Test
    void unauthenticatedGet() {
        makeGetRequest(symbol)
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(user)
    void emptyPosition() {
        when(getStockPositionService.get(user, symbol)).thenReturn(Mono.empty());
        BigDecimal fakeMarketPrice = BigDecimal.valueOf(DomainModelFaker.faker.number()
                .randomDouble(4, 0, 1_000_000));
        when(getStockMarketValueService.get(eq(symbol), any(BigDecimal.class))).thenReturn(Mono.just((fakeMarketPrice)));
        makeGetRequest(symbol)
                //assert
                .expectStatus().isOk()
                .expectBody(Void.class);
    }
    WebTestClient.ResponseSpec makeGetRequest(String symbol) {
        return client.get().uri("/stock-position-market-value/" + symbol)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
    }
}
