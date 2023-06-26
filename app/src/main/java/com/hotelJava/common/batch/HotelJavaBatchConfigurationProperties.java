package com.hotelJava.common.batch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "hotel-java.batch")
public class HotelJavaBatchConfigurationProperties {

    private final StockProperties stock;

    HotelJavaBatchConfigurationProperties() {
        this.stock = new StockProperties();
    }

    @Getter
    @Setter
    public class StockProperties {

        private int chunkSize;
        private int day;
        private long quantity;
    }
}
