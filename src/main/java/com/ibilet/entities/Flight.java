package com.ibilet.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Data
@Getter
@Setter
public class Flight {
    private final String code = UUID.randomUUID().toString();
    private String planeType;
    private int totalSeats;
    private Map<String, Double> prices;
    private String route;

}
