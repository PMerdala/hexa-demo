package pl.merdala.hexademo.adapter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.merdala.hexademo.domain.DomainModelFaker;
import pl.merdala.hexademo.domain.service.GetStockMarketPricePort;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Tag("Spring")
class GetStockMarketPricePortIntegrationTest {

    @Autowired
    GetStockMarketPricePort sut;

    @Test
    void get() {
        //arrange
        String symbol = DomainModelFaker.fakeStockSymbol();
        //act
        Mono<BigDecimal> result = sut.get(symbol).log();
        //assert
        StepVerifier.create(result)
                .assertNext(item ->
                        assertAll(
                                () -> assertThat(item).isGreaterThanOrEqualTo(BigDecimal.ZERO)
                        )
                ).verifyComplete();
    }
}