package pl.merdala.hexademo.domain.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import pl.merdala.hexademo.domain.DomainModelFaker;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertAll;

@DataMongoTest
@Tag("Spring")
class StockPositionsRepositoryIntegrationTest {

    @Autowired
    StockPositionsRepository repository;

    @Test
    void findByUserAndSymbol() {
        //arrange
        String user = DomainModelFaker.fakeUser();
        String symbol = DomainModelFaker.fakeStockSymbol();
        StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(user, symbol);
        repository.deleteAll()
                .then(repository.insert(fakeStockPosition))
                .block();//make sure it's completed
        //act
        Mono<StockPosition> result = repository.findOneByUserAndSymbol(user, symbol);

        //assert
        StepVerifier.create(result)
                .assertNext(stockPosition ->
                        assertAll(
                                () -> Assertions.assertThat(stockPosition).isInstanceOf(StockPosition.class),
                                () -> Assertions.assertThat(stockPosition.getSymbol()).isEqualTo(fakeStockPosition.getSymbol()),
                                () -> Assertions.assertThat(stockPosition.getUser()).isEqualTo(fakeStockPosition.getUser()),
                                () -> Assertions.assertThat(stockPosition.getQuantity()).isEqualTo(fakeStockPosition.getQuantity()),
                                () -> Assertions.assertThat(stockPosition.getCost()).isEqualTo(fakeStockPosition.getCost()),
                                () -> Assertions.assertThat(stockPosition.getCurrencyCode()).isEqualTo(fakeStockPosition.getCurrencyCode())
                        )
                )
                .verifyComplete();
    }

    @Test
    void findByUserAndSymbolEmpty() {
        //arrange
        String user = DomainModelFaker.fakeUser();
        String symbol = DomainModelFaker.fakeStockSymbol();
        repository.deleteAll().block();
        //act
        Mono<StockPosition> result = repository.findOneByUserAndSymbol(user, symbol);
        //assert
        StepVerifier.create(result)
                .verifyComplete();
    }
}