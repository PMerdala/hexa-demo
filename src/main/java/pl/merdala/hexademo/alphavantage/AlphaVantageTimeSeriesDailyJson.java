package pl.merdala.hexademo.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaVantageTimeSeriesDailyJson {
    @JsonProperty("Meta Data")
    private AlphaVantageTimeSeriesDailyJsonMetaData metaData;
    @JsonProperty("Time Series (Daily)")
    private Map<String,AlphaVantageTimeSeriesDailyJsonDaily> daily;
}
