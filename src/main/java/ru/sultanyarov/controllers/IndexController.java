package ru.sultanyarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sultanyarov.models.BlogPost;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String moveToIndex(Model model) {
        String pattern = "d MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        final var build = BlogPost.builder()
                .postAuthor("Владислав Султаняров")
                .postDate(simpleDateFormat.format(new Date()))
                .postDescription("Краткая часть поста")
                .postTitle("Название поста")
                .build();

        model.addAttribute("posts", Collections.singleton(build));
        return "index";
    }

    @GetMapping("/about")
    public String moveToAbout() {
        return "about";
    }
}
