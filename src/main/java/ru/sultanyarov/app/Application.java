package ru.sultanyarov.app;

import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.gitlab4j.api.GitLabApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.sultanyarov.repositories.UsersRepository;

import java.util.function.Supplier;

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
    public GitHubClient getGitHubConnection(UsersRepository usersRepository) {
        final var optionalUser = usersRepository.findById(new ObjectId("5ec02981c533f80d2cebb73d"));
        return optionalUser.map(user -> new GitHubClient().setOAuth2Token(user.getGithubKey()))
                .orElseThrow((Supplier<Throwable>) () -> new RuntimeException("User not found!"));
    }

    @SneakyThrows
    @Bean
    public GitLabApi getGitLabConnection(UsersRepository usersRepository) {
        final var optionalUser = usersRepository.findById(new ObjectId("5ec02981c533f80d2cebb73d"));
        return optionalUser.map(user -> new GitLabApi("https://gitlab.com", user.getGitlabKey()))
                .orElseThrow((Supplier<Throwable>) () -> new RuntimeException("User not found!"));
    }
}
