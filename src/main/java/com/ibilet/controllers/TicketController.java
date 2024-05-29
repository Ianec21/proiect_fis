package com.ibilet.controllers;

import com.ibilet.entities.Ticket;
import com.ibilet.services.TicketService;
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
    public String createTicket(Model model, String firstName, String lastName, String email, String phoneNumber, String age, String paymentMethod) throws ExecutionException, InterruptedException {
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

        ticketService.createTicket(ticket);

        model.addAttribute("error", "");

        return "ticket-success";
    }

    @GetMapping("/tickets")
    public List<Ticket> getTickets(@RequestBody Ticket ticket){
        return null;
    }

    @GetMapping("/ticket-buy")
    public String openReserveTicketPage(){
        return "ticket-buy";
    }
}
