package pl.merdala.hexademo.domain;

import com.github.javafaker.Faker;
import pl.merdala.hexademo.domain.service.StockPosition;

import java.math.BigDecimal;

public class DomainModelFaker {
    public final static Faker faker = Faker.instance();

    public static StockPosition fakeStockPosition(String user, String symbol) {
        return new StockPosition(symbol,
                user,
                fakeQuantity(),
                faker.currency().code(),
                fakeAmount());
    }

    public static BigDecimal fakeAmount() {
        return BigDecimal.valueOf(faker.number().randomDouble(4, 0, 1_000_000));
    }

    public static BigDecimal fakeQuantity() {
        return BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1_000_000));
    }


    public static String fakeUser() {
        return faker.name().username();
    }

    public static String fakeStockSymbol() {
        return faker.stock().nsdqSymbol();
    }
}