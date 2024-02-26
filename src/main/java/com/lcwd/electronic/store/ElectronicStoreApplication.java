package com.lcwd.electronic.store;

import com.lcwd.electronic.store.config.ImagePath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ElectronicStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreApplication.class, args);
        System.out.println("Running electronic store project...");

    }

}
