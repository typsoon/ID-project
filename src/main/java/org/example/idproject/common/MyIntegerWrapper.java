package org.example.idproject.common;

import java.util.Objects;

public class MyIntegerWrapper {
    private final Integer value;

    public Integer getValue() {
        return value;
    }

    public MyIntegerWrapper(String value) {
        this.value = Integer.parseInt(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyIntegerWrapper that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
