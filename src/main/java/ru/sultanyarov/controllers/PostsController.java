package ru.sultanyarov.controllers;

import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.sultanyarov.models.BlogPost;
import ru.sultanyarov.repositories.UsersRepository;
import ru.sultanyarov.service.PostService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

@Controller
public class PostsController {
    private final PostService postService;
    private final UsersRepository usersRepository;

    public PostsController(PostService postService, UsersRepository usersRepository) {
        this.postService = postService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/create_post")
    public String moveToCreatePost(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "createPost";
    }

    @PostMapping("/create_post")
    public Callable<RedirectView> createNewPost(@ModelAttribute BlogPost blogPost) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final var name = (User) auth.getPrincipal();

        final var userOptional = usersRepository.findByName(name.getUsername());
        final var fullName = userOptional.orElse(ru.sultanyarov.models.User.builder()
                .fullName("Пользователь Неизвестен")
                .build()).getFullName();
        if (!blogPost.getPostFullText().isEmpty()) {
            var pattern = "d MMMM yyyy";
            var simpleDateFormat = new SimpleDateFormat(pattern);
            blogPost.setPostDate(simpleDateFormat.format(new Date()));
            blogPost.setPostAuthor(fullName);

            return () -> postService.savePost(blogPost)
                    .thenApply(blogPost1 -> new RedirectView("/"))
                    .get();
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
