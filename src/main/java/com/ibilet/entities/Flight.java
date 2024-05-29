package com.ibilet.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Flight {
    public enum FlightType {
        DUS, DUS_INTORS
    }

    private String code;
    private String planeType;
    private int seatsEconomy;
    private int seatsFirst;
    private double economyPrice;
    private double firstPrice;
    private String departureCity;
    private String arrivalCity;
    private String departureHour;
    private String arrivalHour;
    private List<String> workingDays;
    private double discountPercentageDus;
    private double discountPercentageIntors;
    private FlightType flightType;
}
