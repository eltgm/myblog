package ru.sultanyarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sultanyarov.models.User;

@Controller
public class LoginController {
    @PostMapping("/login")
    public void login(@ModelAttribute User user) {
        System.out.println(user);
    }
}
