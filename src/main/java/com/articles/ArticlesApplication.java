package com.articles;


import com.articles.enums.Role;
import com.articles.model.User;
import com.articles.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@OpenAPIDefinition(info = @Info(title = "Article", version = "1.0"),
        servers = {@Server(url = "http://localhost:8080/", description = "Main server v1")})
@SpringBootApplication
@RequiredArgsConstructor
public class ArticlesApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ArticlesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User(1L, "Admin", "Admin", "Admin@mail.ru", bCryptPasswordEncoder.encode("Admin"),
                Role.ADMIN, true, null));
    }
}
