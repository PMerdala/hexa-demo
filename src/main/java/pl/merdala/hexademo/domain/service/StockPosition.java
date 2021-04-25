package pl.merdala.hexademo.domain.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPosition {
    private String symbol;
    private String user;
    private BigDecimal quantity;
    private String currencyCode;
    private BigDecimal cost;
}
