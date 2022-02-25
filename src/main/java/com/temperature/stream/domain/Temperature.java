package com.temperature.stream.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

//@Getter -> getter는 public level에서만 접근가능하다..
public class Temperature {
    private final double value;

    public Temperature(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
