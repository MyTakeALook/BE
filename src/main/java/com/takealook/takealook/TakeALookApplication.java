package com.takealook.takealook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication

public class TakeALookApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeALookApplication.class, args);
    }
    // static {
    // System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    // }
}
