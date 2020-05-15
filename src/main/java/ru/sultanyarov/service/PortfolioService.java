package ru.sultanyarov.service;

import ru.sultanyarov.models.PortfolioItem;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PortfolioService {
    CompletableFuture<List<PortfolioItem>> getAllRepos();
}
