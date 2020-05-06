package ru.sultanyarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.sultanyarov.models.BlogPost;

@Controller
public class CreatePostController {
    @GetMapping("/create_post")
    public String moveToCreatePost(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "createPost";
    }

    @PostMapping("/create_post")
    public RedirectView  createNewPost(@ModelAttribute BlogPost blogPost) {
        return new RedirectView( "/");
    }
}
