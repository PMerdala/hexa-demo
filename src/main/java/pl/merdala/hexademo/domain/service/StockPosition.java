package pl.merdala.hexademo.domain.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockPosition {
    private String symbol;
    private BigDecimal quantity;
    private String currencyCode;
    private BigDecimal cost;
}
