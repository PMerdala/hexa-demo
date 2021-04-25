package pl.merdala.hexademo.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.merdala.hexademo.domain.DomainModelFaker;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStockMarketValueServiceTest {

    @Mock
    GetStockMarketPricePort getStockMarketPricePort;

    GetStockMarketValueService sut;

    @BeforeEach
    void setUp() {
        sut = new GetStockMarketValueService(getStockMarketPricePort);
    }

    @Test
    void get() {
        //arrange
        String symbol = DomainModelFaker.fakeStockSymbol();
        BigDecimal fakeQuantity = DomainModelFaker.fakeQuantity();
        BigDecimal fakePrice = DomainModelFaker.fakeAmount();
        when(getStockMarketPricePort.get(symbol)).thenReturn(Mono.just(fakePrice));

        //act
        Mono<BigDecimal> marketValue = sut.get(symbol, fakeQuantity);

        //assert
        StepVerifier.create(marketValue)
            .expectNextMatches(amount-> amount.equals(fakeQuantity.multiply(fakePrice)))
            .verifyComplete();
    }
}