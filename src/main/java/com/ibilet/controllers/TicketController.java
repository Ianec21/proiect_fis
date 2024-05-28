package com.ibilet.controllers;

import com.ibilet.entities.Ticket;
import com.ibilet.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class TicketController {
    @Autowired
    public TicketService ticketService;

    @PostMapping("/rezervare-bilet")
    public String createTicket(Model model, String nume, String prenume, String email, String telefon, String varsta, String tip_plata) throws ExecutionException, InterruptedException {
        if(nume.isEmpty() || prenume.isEmpty() || email.isEmpty() || telefon.isEmpty() || varsta.isEmpty() || tip_plata.isEmpty()){
            model.addAttribute("error", "Vă rugăm să îndepliniți toate câmpurile goale!");
            return "rezervare-bilet";
        }

        model.addAttribute("error", "");

        return "rezervare-success";
    }

    @GetMapping("/ticket")
    public List<Ticket> getTickets(@RequestBody Ticket ticket){
        return null;
    }

    @GetMapping("/rezervare-bilet")
    public String openReserveTicketPage(){
        return "rezervare-bilet";
    }
}
