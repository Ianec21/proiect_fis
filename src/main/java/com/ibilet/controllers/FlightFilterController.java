package com.ibilet.controllers;

import com.ibilet.entities.Flight;
import com.ibilet.entities.FlightFilter;
import com.ibilet.services.FlightFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Controller
public class FlightFilterController {
    @Autowired
    FlightFilterService flightFilterService;
    @PostMapping("/search")
    public String searchFlights(Model model, String arrival,String reservationType, String departureDate, String arrivalDate, int adults, int children, String departure, String flightType) throws ExecutionException, InterruptedException {
        if (arrival == null || departure == null || arrival.isEmpty()||departure.isEmpty()) {
            return "redirect:/booking";
        }
        /*if(departureDate == null || arrivalDate == null){
            return "redirect:/booking";
        }
        if (flightType.equals(FlightFilter.FlightType.OneWay.toString()) &&
                flightType.equals(FlightFilter.FlightType.RoundTrip.toString())) {
            return "redirect:/booking";
        }
        if(reservationType == null || reservationType.isEmpty()){
            return "redirect:/booking";
        }*/

        List<Flight> flight = flightFilterService.FilterFlights(arrival,reservationType,departureDate,arrivalDate,children,adults, departure, flightType);
        model.addAttribute("flights",flight);
        return "search";
    }

    @GetMapping("/booking")
    public String openBooking(){

        return "booking";
    }
}
