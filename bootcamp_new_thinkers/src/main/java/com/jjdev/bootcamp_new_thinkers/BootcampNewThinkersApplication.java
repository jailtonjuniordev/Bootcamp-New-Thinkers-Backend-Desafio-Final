package com.jjdev.bootcamp_new_thinkers;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class BootcampNewThinkersApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        System.setProperty("database_username", Objects.requireNonNull(dotenv.get("DATABASE_USERNAME")));
        System.setProperty("database_password", Objects.requireNonNull(dotenv.get("DATABASE_PASSWORD")));

        SpringApplication.run(BootcampNewThinkersApplication.class, args);
    }

}
