package pl.merdala.hexademo.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetStockPositionService {

    private final StockPositionsRepository repository;

    public GetStockPositionService(StockPositionsRepository repository) {
        this.repository = repository;
    }

    public Mono<StockPosition> get(String user, String symbol) {
        return repository.findOneByUserAndSymbol(user,symbol);
    }
}
