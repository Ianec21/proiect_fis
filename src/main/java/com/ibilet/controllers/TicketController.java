package com.ibilet.controllers;

import com.ibilet.entities.Ticket;
import com.ibilet.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class TicketController {
    @Autowired
    public TicketService ticketService;

    @PostMapping("/ticket/create")
    public String createTicket(@RequestBody Ticket ticket) throws ExecutionException, InterruptedException {
        return ticketService.createTicket(ticket);
    }

    @GetMapping("/ticket")
    public List<Ticket> getTickets(@RequestBody Ticket ticket){
        return null;
    }
}
