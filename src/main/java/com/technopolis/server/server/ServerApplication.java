package com.technopolis.server.server;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configurable
@ComponentScan("com.technopolis.server.database")
@ComponentScan("com.technopolis.server.fetcher")
@EntityScan("com.technopolis.server.database.model")
@EnableJpaRepositories("com.technopolis.server.database.repository")
public class ServerApplication {
    /*
     * сделать toString
     * отрефакторить весь код
     * */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
