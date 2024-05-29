package com.ibilet.entities;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class FlightFilter {
    public enum FlightType{Dus,DusIntors};
    private String departureCity;
    private String destinationCity;
    private int seatNumber;
    private LocalDate flightDate;
}
