package ru.sultanyarov.app;

import lombok.SneakyThrows;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.gitlab4j.api.GitLabApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.file.Files;

@EnableWebMvc
@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "ru.sultanyarov")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "threadPoolExecutor")
    @Primary
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setThreadNamePrefix("Async-");

        return executor;
    }

    @SneakyThrows
    @Bean
    public GitHubClient getGitHubConnection() {
        final var token = Files.readString(new ClassPathResource(
                ".github").getFile().toPath());
        return new GitHubClient().setOAuth2Token(token);
    }

    @SneakyThrows
    @Bean
    public GitLabApi getGitLabConnection() {
        final var token = Files.readString(new ClassPathResource(
                ".gitlab").getFile().toPath());
        return new GitLabApi("https://gitlab.com", token);
    }
}
