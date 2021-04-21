package pl.merdala.hexademo.domain.service;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class GetStockMarketValueService {
    public Mono<BigDecimal> get(String symbol, BigDecimal quantity) {
        return Mono.empty();
    }
}
