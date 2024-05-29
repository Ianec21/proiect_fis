package com.ibilet.controllers;

import com.ibilet.entities.User;
import com.ibilet.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {
        try {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                // Save user details in the session
                session.setAttribute("loggedInUser", user);

                if (user.getRole() == User.Role.CLIENT) {
                    return "redirect:/client/home";
                } else if (user.getRole() == User.Role.AIRLINE) {
                    return "redirect:/airline/home";
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String role, Model model) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(User.Role.valueOf(role.toUpperCase()));
            userService.createUser(user);
            return "redirect:/login";
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid role");
        }
        model.addAttribute("error", "Registration failed");
        return "register";
    }
}
