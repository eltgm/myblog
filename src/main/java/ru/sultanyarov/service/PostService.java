package ru.sultanyarov.service;

import org.bson.types.ObjectId;
import ru.sultanyarov.models.BlogPost;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PostService {
    CompletableFuture<List<BlogPost>> getAllPosts();

    CompletableFuture<Optional<BlogPost>> getPostById(ObjectId postId);

    CompletableFuture<BlogPost> savePost(BlogPost blogPost);
}
