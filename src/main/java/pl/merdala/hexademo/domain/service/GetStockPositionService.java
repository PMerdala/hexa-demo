package pl.merdala.hexademo.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetStockPositionService {

    public Mono<StockPosition> get(String user, String symbol) {
        return Mono.empty();
    }
}
