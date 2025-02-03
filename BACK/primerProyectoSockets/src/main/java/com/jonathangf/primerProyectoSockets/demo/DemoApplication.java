package com.jonathangf.primerProyectoSockets.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(scanBasePackages = {"com.jonathangf.primerProyectoSockets.config",
        "com.jonathangf.primerProyectoSockets.controller",
        "com.jonathangf.primerProyectoSockets.models",
        "com.jonathangf.primerProyectoSockets.services",
})
@EnableMongoRepositories(basePackages = "com.jonathangf.primerProyectoSockets.repositories")
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
