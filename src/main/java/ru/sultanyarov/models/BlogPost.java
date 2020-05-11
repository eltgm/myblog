package ru.sultanyarov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("blog_posts")
public class BlogPost {
    private String postTitle;
    private String postImage;
    private String postDate;
    private String postAuthor;
    private String postDescription;
    private String postFullText;
}
