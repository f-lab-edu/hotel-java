package com.hotelJava.common.util;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class Base32Util {

    public String encode(String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

        return new Base32().encodeToString(bytes);
    }

    public String decode(String content) {
        return new String(new Base32().decode(content));
    }
}
