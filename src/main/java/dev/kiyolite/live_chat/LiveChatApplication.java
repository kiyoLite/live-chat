package dev.kiyolite.live_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"dev.kiyolite.live_chat.Persistence.Repository","dev.kiyolite.live_chat.Persistence.DAO"})
public class LiveChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveChatApplication.class, args);
    }

}
