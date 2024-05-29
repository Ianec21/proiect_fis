package com.ibilet.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Flight {
    private String code;
    private String planeType;
    private int totalSeats;
    private double economyPrice;
    private double standardPrice;
    private Map<String, String> route;
}
