package pl.merdala.hexademo.domain.service;

import reactor.core.publisher.Mono;

public interface StockPositionsRepository {
    Mono<StockPosition> findOneByUserAndSymbol(String user, String symbol);

    Mono<Void> deleteAll();

    Mono<StockPosition> insert(StockPosition stockPosition);
}
