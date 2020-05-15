package ru.sultanyarov.service;

import lombok.SneakyThrows;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Visibility;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sultanyarov.models.PortfolioItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Async
@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final RepositoryService githubRepositories;
    private final GitLabApi gitLabRepositories;

    public PortfolioServiceImpl(GitHubClient gitHubClient, GitLabApi gitLabRepositories) {
        this.githubRepositories = new RepositoryService(gitHubClient);
        this.gitLabRepositories = gitLabRepositories;
    }

    @SneakyThrows
    @Override
    public CompletableFuture<List<PortfolioItem>> getAllRepos() {
        var githubRepos = CompletableFuture.supplyAsync((Supplier<List<Repository>>) () -> {
            try {
                return githubRepositories.getRepositories();
            } catch (IOException e) {
                e.fillInStackTrace();
                return Collections.emptyList();
            }
        }).thenApply((Function<List<Repository>, List<PortfolioItem>>) repositories -> {
            if (repositories.isEmpty())
                return Collections.emptyList();

            var portfolioItems = new ArrayList<PortfolioItem>();
            repositories.forEach(repository -> portfolioItems.add(PortfolioItem.builder()
                    .about(repository.getDescription())
                    .url(repository.getHtmlUrl())
                    .repoName(repository.getName())
                    .lastModify(repository.getUpdatedAt())
                    .build()));

            return portfolioItems;
        });

        var gitlabRepos = CompletableFuture.supplyAsync((Supplier<List<Project>>) () -> {
            try {
                return gitLabRepositories.getProjectApi().getMemberProjects();
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }).thenApply((Function<List<Project>, List<PortfolioItem>>) projects -> {
            if (projects.isEmpty())
                return Collections.emptyList();
            var portfolioItems = new ArrayList<PortfolioItem>();
            projects.forEach(project -> {
                if (project.getVisibility().equals(Visibility.PUBLIC)) {
                    portfolioItems.add(PortfolioItem.builder()
                            .repoName(project.getName())
                            .url(project.getWebUrl())
                            .about(project.getDescription())
                            .lastModify(project.getLastActivityAt())
                            .build());
                }
            });

            return portfolioItems;
        });


        return gitlabRepos.thenCombine(githubRepos, (portfolioItems, portfolioItems2) ->
                Stream.concat(portfolioItems.stream(), portfolioItems2.stream())
                        .collect(Collectors.toList()))
                .thenApply(portfolioItems -> {
                    portfolioItems.sort((o1, o2) -> {
                        if (o1.getLastModify() == null || o2.getLastModify() == null)
                            return 0;
                        return o1.getLastModify().compareTo(o2.getLastModify());
                    });
                    Collections.reverse(portfolioItems);
                    return portfolioItems;
                });
    }
}
