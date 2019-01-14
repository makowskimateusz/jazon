package com.zendesk;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

final class ActualTranslators {
    private ActualTranslators() {
    }

    static ActualJsonObject actualObject(Map<String, Object> objectsMap) {
        // "what is actual" responsibility used in iteration
        Map<String, Actual> map = objectsMap.entrySet()
                .stream()
                .collect(
                        toMap(
                                Map.Entry::getKey,
                                e -> actual(e.getValue())
                        )
                );
        return new ActualJsonObject(map);
    }

    private static Actual actual(Object object) {
        // TODO make this customisable+overridable with configuration
        if (object instanceof Map) {
            return actualObject((Map<String, Object>) object);
        } else if (object instanceof Number) {
            return new ActualJsonNumber((Number) object);
        } else if (object instanceof String) {
            return new ActualJsonString((String) object);
        } else if (object == null) {
            return new ActualJsonNull();
        }
        throw new IllegalArgumentException();
    }
}
