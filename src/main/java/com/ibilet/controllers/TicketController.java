package com.ibilet.controllers;

import com.ibilet.entities.Ticket;
import com.ibilet.entities.User;
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

    @PostMapping("/ticket-buy")
    public String createTicket(Model model, HttpSession session, String flightId, String firstName, String lastName, String email, String phoneNumber, String age, String paymentMethod) throws ExecutionException, InterruptedException {
        System.out.println(flightId);
        model.addAttribute("flightId", flightId);
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || age.isEmpty() || paymentMethod.isEmpty()){
            model.addAttribute("error", "Please fill in all fields!");
            return "ticket-buy";
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
    public String openReserveTicketPage(Model model, HttpSession session, String flightId){
        if(session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }

        model.addAttribute("flightId", flightId);

        return "ticket-buy";
    }
}
