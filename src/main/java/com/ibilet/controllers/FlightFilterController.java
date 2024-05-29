package com.ibilet.controllers;

import ch.qos.logback.core.model.Model;
import com.ibilet.entities.FlightFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;


@Controller
public class FlightFilterController {
    @PostMapping("/search")
    public String searchFlights(Model model, String arrivalCity,String reservationType, String departureDate,String arrivalDate, int seatNumber, String departureCity, String flightType){
        if (arrivalCity == null || departureCity == null || arrivalCity.isEmpty()||departureCity.isEmpty()) {
            return "return:/booking";
        }
        if(departureDate == null || arrivalDate == null){
            return "return:/booking";
        }
        if(!flightType.equals(FlightFilter.FlightType.OneWay) && !flightType.equals(FlightFilter.FlightType.RoundTrip)){
            return "return:/booking";
        }
        if(reservationType == null || reservationType.isEmpty()){
            return "return:/booking";
        }



        return "search";
    }

    @GetMapping("/booking")
    public String openBooking(){

        return "booking";
    }
}
