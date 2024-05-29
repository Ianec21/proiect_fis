package com.ibilet.controllers;

import com.ibilet.entities.User;
import com.ibilet.entities.Flight;
import com.ibilet.services.FlightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Controller
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/")
    public String openHomePage(Model model, HttpSession session) throws ExecutionException, InterruptedException {
        List<Flight> flights = flightService.getAllFlights(); // Assuming you have a method in FlightService to retrieve all flights
        System.out.println(flights.toString());
        model.addAttribute("flights", flights);

        return "home";
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
    @GetMapping("/flights")
    public String showFlights(Model model) throws ExecutionException, InterruptedException {
        List<Flight> flights = flightService.getAllFlights(); // Assuming you have a method in FlightService to retrieve all flights
        model.addAttribute("flights", flights);
        return "flights";
    }

    @PostMapping("/add-flight")
    public String addFlight(String planeType, int totalSeats, double economyPrice, double standardPrice, String route, HttpSession session, Model model) throws ExecutionException, InterruptedException {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null || user.getRole() != User.Role.AIRLINE) {
            return "redirect:/"; // or an error page
        }

        // Validation logic
        if (planeType.isEmpty() || totalSeats <= 0 || economyPrice <= 0 || standardPrice <= 0 || route.isEmpty()) {
            model.addAttribute("error", "Please fill in all required fields!");
            return "add-flight";
        }

        model.addAttribute("error", "");

        Flight flight = new Flight();
        flight.setCode(UUID.randomUUID().toString());
        flight.setPlaneType(planeType);
        flight.setTotalSeats(totalSeats);
        flight.setEconomyPrice(economyPrice);
        flight.setStandardPrice(standardPrice);

        Map<String, String> routeMap = new HashMap<>();
        String[] routes = route.split("\n");

        for (String sRoute : routes) {
            String[] routeData = sRoute.split(", ");
            routeMap.put(routeData[0], routeData[1]);
        }
        flight.setRoute(routeMap);

        flightService.createFlight(flight); // Save the flight using the FlightService
        return "redirect:/";
    }
}
