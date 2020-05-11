package ru.sultanyarov;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sultanyarov.models.BlogPost;
import ru.sultanyarov.repositories.BlogPostRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class IndexServiceImpl implements IndexService {
    private final BlogPostRepository blogPostRepository;

    public IndexServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public CompletableFuture<List<BlogPost>> getAllPosts() {
        final var all = blogPostRepository.findAll();
        return CompletableFuture.completedFuture(all);
    }
}
