package ru.sultanyarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sultanyarov.service.PortfolioService;

import java.util.concurrent.Callable;

@Controller
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/work")
    public Callable<String> moveToPortfolio(Model model) {
        return () -> portfolioService.getAllRepos()
                .thenApply(portfolioItems -> {
                    model.addAttribute("repositories", portfolioItems);
                    return "work";
                })
                .get();
    }
}
