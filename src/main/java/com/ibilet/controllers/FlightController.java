package com.ibilet.controllers;

import com.ibilet.entities.Flight;
import com.ibilet.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/add-flight")
    public String showAddFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "add-flight";
    }

    @PostMapping("/add-flight")
    public String addFlight(@ModelAttribute Flight flight) {
        flightService.saveFlight(flight); // Save the flight using the FlightService
        return "redirect:/home";
    }
}
