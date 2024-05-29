package com.ibilet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlightFilterController {
    @GetMapping("/booking")
    public String openBooking(){
        return "booking";
    }
}
