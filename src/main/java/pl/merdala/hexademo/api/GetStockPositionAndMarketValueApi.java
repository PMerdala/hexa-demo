package pl.merdala.hexademo.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.merdala.hexademo.domain.service.GetStockMarketValueService;
import pl.merdala.hexademo.domain.service.GetStockPositionService;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class GetStockPositionAndMarketValueApi {

    private final GetStockPositionService getStockPositionService;
    private final GetStockMarketValueService getStockMarketValueService;

    public GetStockPositionAndMarketValueApi(GetStockPositionService getStockPositionService, GetStockMarketValueService getStockMarketValueService) {
        this.getStockPositionService = getStockPositionService;
        this.getStockMarketValueService = getStockMarketValueService;
    }

    @GetMapping("/stock-position-market-value/{symbol}")
    Mono<GetStockPositionAndMarketValueApitResponseDto> getPositionAndMarketValue(
            @AuthenticationPrincipal Mono<Principal> principalMono,
            @PathVariable("symbol") String symbol) {
        return principalMono
                .flatMap(principal -> getStockPositionService.get(principal.getName(), symbol))
                .zipWhen(stockPosition -> getStockMarketValueService.get(symbol, stockPosition.getQuantity()))
                .map(stockPositionAndMarketValue -> new GetStockPositionAndMarketValueApitResponseDto(
                        symbol,
                        stockPositionAndMarketValue.getT1().getQuantity(),
                        stockPositionAndMarketValue.getT1().getCurrencyCode(),
                        stockPositionAndMarketValue.getT1().getCost()
                        , stockPositionAndMarketValue.getT2()));
    }
}
