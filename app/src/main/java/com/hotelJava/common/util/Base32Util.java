package com.hotelJava.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Component;

@Component
public class Base32Util {

    public String encode(String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

        return new Base32().encodeToString(bytes);
    }

    public Optional<String> decode(String content) {
        return Optional.of(new String(new Base32().decode(content)));
    }
}
