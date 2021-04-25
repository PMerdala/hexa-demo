package pl.merdala.hexademo.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.merdala.hexademo.domain.DomainModelFaker;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStockPositionServiceTest {

    @Mock
    private StockPositionsRepository repository;

    private  GetStockPositionService sut;

    @BeforeEach
    void setUp() {
        sut = new GetStockPositionService(repository);
    }

    @Test
    void get() {
        //arrange
        String user = DomainModelFaker.fakeUser();
        String symbol = DomainModelFaker.fakeStockSymbol();
        StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(user, symbol);
        when(repository.findOneByUserAndSymbol(user,symbol)).thenReturn(Mono.just(fakeStockPosition));

        //act

        Mono<StockPosition> result = sut.get(user,symbol);
        //assert
        StepVerifier.create(result)
                .expectNext(fakeStockPosition)
                .verifyComplete();
    }
}