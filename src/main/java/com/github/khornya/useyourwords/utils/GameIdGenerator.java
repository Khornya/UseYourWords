package com.github.khornya.useyourwords.utils;

import org.springframework.stereotype.Component;
import org.apache.commons.text.RandomStringGenerator;
import java.util.function.Supplier;

@Component
public class GameIdGenerator implements Supplier<String> {

    private RandomStringGenerator gameIdGenerator;

    public GameIdGenerator() {
        gameIdGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'Z')
                .filteredBy(t -> t >= '0' && t <= '9', t -> t >= 'A' && t <= 'Z')
                .build();
    }

    @Override
    public String get() {
        return gameIdGenerator.generate(6);
    }

}
