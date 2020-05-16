package ru.sultanyarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.sultanyarov.models.Email;
import ru.sultanyarov.service.EmailService;

import java.util.concurrent.Callable;

@Controller
public class ContactController {
    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/contact")
    public String moveToContact(Model model) {
        model.addAttribute("email", new Email());
        return "contact";
    }

    @PostMapping("/send_email")
    public Callable<RedirectView> sendMail(@ModelAttribute Email email) {
        if (email != null) {
            return () -> {
                emailService.sendEmail(email);
                return new RedirectView("/");
            };
        } else {
            return () -> new RedirectView("/");
        }
    }
}
