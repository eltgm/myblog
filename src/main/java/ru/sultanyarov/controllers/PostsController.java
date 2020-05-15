package ru.sultanyarov.controllers;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.sultanyarov.models.BlogPost;
import ru.sultanyarov.service.PostService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

@Controller
public class PostsController {
    private final PostService postService;

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/create_post")
    public String moveToCreatePost(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "createPost";
    }

    @PostMapping("/create_post")
    public Callable<RedirectView> createNewPost(@ModelAttribute BlogPost blogPost) {
        if (blogPost != null) {
            var pattern = "d MMMM yyyy";
            var simpleDateFormat = new SimpleDateFormat(pattern);
            blogPost.setPostDate(simpleDateFormat.format(new Date()));

            return () -> postService.savePost(blogPost).thenApply(blogPost1 -> new RedirectView("/")).get();
        } else {
            return () -> new RedirectView("/");
        }
    }

    @GetMapping("/single")
    public Callable<String> getPost(String id, Model model) {
        return () -> postService.getPostById(new ObjectId(id))
                .thenApply(blogPost -> {
                    if (blogPost.isPresent()) {
                        model.addAttribute("post", blogPost.get());
                        return "single";
                    }

                    return "/";
                })
                .get();
    }
}
