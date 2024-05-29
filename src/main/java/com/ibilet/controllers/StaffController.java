package com.ibilet.controllers;

import com.ibilet.entities.Ticket;
import com.ibilet.entities.User;
import com.ibilet.services.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class StaffController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/staff")
    public String openStaff(Model model, HttpSession session) throws ExecutionException, InterruptedException {
        if(session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }

        User user = (User)session.getAttribute("loggedInUser");
        if(user.getRole() == User.Role.CLIENT){
            return "redirect:/";
        }

        List<Ticket> tickets = ticketService.getUnpaidTickets();
        model.addAttribute("tickets", tickets);

        return "staff";
    }
}
