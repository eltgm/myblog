package ru.sultanyarov;

import ru.sultanyarov.models.BlogPost;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IndexService {
    CompletableFuture<List<BlogPost>> getAllPosts();
}
