package com.gymandkhana.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class AttendanceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttendanceServiceApplication.class, args);
    }
}
