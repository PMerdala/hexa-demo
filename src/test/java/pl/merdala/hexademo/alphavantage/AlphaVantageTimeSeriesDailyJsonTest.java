package pl.merdala.hexademo.alphavantage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class AlphaVantageTimeSeriesDailyJsonTest {

    public static final String ALPHAVANTAGE_SAMPLES_TIMESERIES_DAILY_JSON = "alphavantage-samples/timeseries-daily.json";
    private AlphaVantageTimeSeriesDailyJson sut = new AlphaVantageTimeSeriesDailyJson();
    ;

    @Test
    void matchesJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new ClassPathResource(ALPHAVANTAGE_SAMPLES_TIMESERIES_DAILY_JSON).getFile();
        AlphaVantageTimeSeriesDailyJson json = mapper.readValue(file, AlphaVantageTimeSeriesDailyJson.class);
        assertThat(json).isNotNull();
    }
}