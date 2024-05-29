package com.ibilet.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FlightDTO {
    private String companyId;
    private String companyName;
    private String code;
    private String departureHour;
    private String arrivalHour;
    private double price;
    private Flight.FlightType flightType;
    private String departureDate;
    private String arrivalDate;
}
