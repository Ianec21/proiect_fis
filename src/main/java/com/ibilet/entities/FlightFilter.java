package com.ibilet.entities;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class FlightFilter {
    public enum FlightType{OneWay,RoundTrip}
    private String departureCity;
    private String arrivalCity;
    private String departureDate;
    private String returnDate;
    private int nrSeatsAdults;
    private int nrSeatsChildren;
    private String reservationType;
    private FlightType flightType;
}
