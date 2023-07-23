package com.stpg.distrinet.services;

import java.util.stream.Collector;

public class StringUtils {
    public static String stripAccents(String s) {

        if (null == s || s.isEmpty()) {
            return s;
        }

        final String[] map = {
                "ÈÉüÚÏÀÂÁÔèéüúïíÍàâôÇçóá",
                "EEUUIAAAOeeuuiiIaaoCcoa"
        };

        return s.chars()
                .mapToObj(c -> (char) (map[0].indexOf(c) > -1 ? map[1].charAt(map[0].indexOf(c)) : c))
                .collect(Collector.of(
                        StringBuilder::new, StringBuilder::append,
                        StringBuilder::append, StringBuilder::toString
                ));
    }
}
