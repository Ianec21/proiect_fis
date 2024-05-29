package com.ibilet.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Flight {
    public enum FlightType {
        OneWay, RoundTrip
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
    private String departureDate;
    private String arrivalDate;
    private double discountPercentageDus;
    private double discountPercentageIntors;
    private FlightType flightType;
    private String companyId;
}
