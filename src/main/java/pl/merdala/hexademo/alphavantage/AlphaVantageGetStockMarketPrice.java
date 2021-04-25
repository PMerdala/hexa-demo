package pl.merdala.hexademo.alphavantage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.merdala.hexademo.domain.service.GetStockMarketPricePort;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class AlphaVantageGetStockMarketPrice implements GetStockMarketPricePort {

    @Value("${alphavantage.apiKey}")
    private String APIKEY = "APIKEY";

    @Override
    public Mono<BigDecimal> get(String symbol) {
        return WebClient.create().get().uri("https://www.alphavantage.co/query",
                uriBuilder -> uriBuilder.queryParam("function", "TIME_SERIES_DAILY")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", APIKEY)
                        .build()
        ).retrieve()
                .bodyToMono(AlphaVantageTimeSeriesDailyJson.class)
                .doOnNext(json->log.debug(json.toString()))
                .map(this::getClosingPrice);
    }

    private BigDecimal getClosingPrice(AlphaVantageTimeSeriesDailyJson alphaVantageTimeSeriesDailyJson){
        return  new BigDecimal(getLatestClosingPrice(alphaVantageTimeSeriesDailyJson.getDaily()));
    }

    private String getLatestClosingPrice(Map<String, AlphaVantageTimeSeriesDailyJsonDaily> daily) {
        String latest = daily.keySet().stream()
                .reduce("", (subLatest, item) -> item.compareToIgnoreCase(subLatest) > 0 ? item : subLatest);
        return daily.get(latest).getClosePrice();
    }
}
