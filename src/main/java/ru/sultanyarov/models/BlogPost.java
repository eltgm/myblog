package ru.sultanyarov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPost {
    private String postTitle;
    private String postImage;
    private String postDate;
    private String postAuthor;
    private String postDescription;
    private String postFullText;
}
