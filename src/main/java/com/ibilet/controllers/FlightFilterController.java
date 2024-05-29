package com.ibilet.controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlightFilterController {
    @PostMapping("/search")
    public String searchFlights(Model model,String destination,String arrival,String adults,String children,String start,String flightType){
        return "/search";
    }
    @GetMapping("/booking")
    public String openBooking(){
        return "booking";
    }
}
