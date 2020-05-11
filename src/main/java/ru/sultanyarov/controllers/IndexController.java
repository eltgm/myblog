package ru.sultanyarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sultanyarov.IndexService;

import java.util.concurrent.Callable;

@Controller
public class IndexController {
    private final IndexService indexService;
    String pattern = "d MMMM yyyy";

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping(value = {"/", "/index"})
    public Callable<String> moveToIndex(Model model) {
        return () -> indexService.getAllPosts()
                .thenApply(blogPosts -> {
                    model.addAttribute("posts", blogPosts);
                    return "index";
                })
                .get();
    }

    @GetMapping("/about")
    public String moveToAbout() {
        return "about";
    }
}
