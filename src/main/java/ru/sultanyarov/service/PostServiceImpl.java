package ru.sultanyarov.service;

import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sultanyarov.models.BlogPost;
import ru.sultanyarov.repositories.BlogPostRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class PostServiceImpl implements PostService {
    private final BlogPostRepository blogPostRepository;

    public PostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public CompletableFuture<List<BlogPost>> getAllPosts() {
        var all = blogPostRepository.findAll();
        Collections.reverse(all);
        return CompletableFuture.completedFuture(all);
    }

    @Override
    public CompletableFuture<Optional<BlogPost>> getPostById(ObjectId postId) {
        return CompletableFuture.completedFuture(blogPostRepository.findById(postId));
    }

    @Override
    public CompletableFuture<BlogPost> savePost(BlogPost blogPost) {
        return CompletableFuture.completedFuture(blogPostRepository.save(blogPost));
    }
}
