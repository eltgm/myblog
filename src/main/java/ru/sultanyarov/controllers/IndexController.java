package ru.sultanyarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sultanyarov.service.PostService;

import java.util.concurrent.Callable;

@Controller
public class IndexController {
    private final PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = {"/", "/index"})
    public Callable<String> moveToIndex(Model model) {
        return () -> postService.getAllPosts()
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
