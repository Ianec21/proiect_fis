package com.ibilet.controllers;

import com.ibilet.entities.Flight;
import com.ibilet.entities.FlightDTO;
import com.ibilet.services.FlightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class HomeController {
    @Autowired
    FlightService flightService;

    @GetMapping("/")
    public String openHomePage(Model model, HttpSession session) throws ExecutionException, InterruptedException {
        List<FlightDTO> flights = flightService.getAllFlights(); // Assuming you have a method in FlightService to retrieve all flights
        System.out.println(flights.toString());
        model.addAttribute("flights", flights);

        return "home";
    }
}
