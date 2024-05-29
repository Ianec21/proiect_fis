package com.ibilet.controllers;

import com.google.api.Http;
import com.ibilet.entities.Flight;
import com.ibilet.entities.Ticket;
import com.ibilet.entities.User;
import com.ibilet.services.FlightService;
import com.ibilet.services.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class TicketController {
    @Autowired
    public TicketService ticketService;

    @Autowired
    public FlightService flightService;

    @PostMapping("/ticket-buy")
    public String createTicket(Model model, HttpSession session, String flightId, String firstName, String lastName, String email, String phoneNumber, String age, String paymentMethod, String cardNumber, String holderName, String expirationData, String cvc, String iban) throws ExecutionException, InterruptedException {
        model.addAttribute("flightId", flightId);

        Flight foundFlight = flightService.getFlightById(flightId);
        model.addAttribute("flightData", foundFlight);

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || age.isEmpty() || paymentMethod.isEmpty()){
            model.addAttribute("error", "Please fill in all fields!");
            return "ticket-buy";
        }

        if (paymentMethod.toUpperCase().equals(Ticket.PaymentMethod.CARD.toString())) {
            if (cardNumber == null || cardNumber.isEmpty() ||
                    holderName == null || holderName.isEmpty() ||
                    expirationData == null || expirationData.isEmpty() ||
                    cvc == null || cvc.isEmpty()) {
                model.addAttribute("error", "Please fill in all fields!");
                return "ticket-buy";
            }
        }

        if (paymentMethod.toUpperCase().equals(Ticket.PaymentMethod.IBAN.toString())) {
            if (iban == null || iban.isEmpty()) {
                model.addAttribute("error", "Please fill in all fields!");
                return "ticket-buy";
            }
        }

        Ticket ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setEmail(email);
        ticket.setPhoneNumber(phoneNumber);
        ticket.setAge(Integer.parseInt(age));
        System.out.println(paymentMethod);
        ticket.setPaymentMethod(Ticket.PaymentMethod.valueOf(paymentMethod.toUpperCase()));
        ticket.setFlightId(flightId);

        if(paymentMethod.toUpperCase().equals(Ticket.PaymentMethod.CARD.toString())){
            ticket.setValidatedCashPayment(false);
        }

        if (paymentMethod.toUpperCase().equals(Ticket.PaymentMethod.CARD.toString())) {
            ticket.setCardNumber(cardNumber);
        }

        if (paymentMethod.toUpperCase().equals(Ticket.PaymentMethod.IBAN.toString())) {
            ticket.setIban(iban);
        }

        User user = (User)session.getAttribute("loggedInUser");
        ticket.setUserID(user.getId());

        ticketService.createTicket(ticket);

        model.addAttribute("error", "");
        model.addAttribute("boughtTicket", ticket);

        return "ticket-success";
    }

    @GetMapping("/tickets")
    public List<Ticket> getTickets(@RequestBody Ticket ticket){
        return null;
    }

    @GetMapping("/ticket-buy")
    public String openReserveTicketPage(Model model, HttpSession session, String flightId) throws ExecutionException, InterruptedException {
        if(session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }

        Flight foundFlight = flightService.getFlightById(flightId);

        model.addAttribute("flightId", flightId);
        model.addAttribute("flightData", foundFlight);

        return "ticket-buy";
    }

    @PostMapping("/ticket-validate")
    public String validateTicket(Model model, HttpSession session, String ticketId) throws ExecutionException, InterruptedException {
        Ticket foundTicket = ticketService.getTicketById(ticketId);
        System.out.println(foundTicket.getId());
        ticketService.validateTicket(foundTicket);

        return "staff";
    }
}
