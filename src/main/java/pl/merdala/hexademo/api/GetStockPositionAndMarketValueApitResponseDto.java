package pl.merdala.hexademo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStockPositionAndMarketValueApitResponseDto {
    private String symbol;
    private Number quantity;
    private String currencyCode;
    private Number cost;
    private Number marketValue;

}
