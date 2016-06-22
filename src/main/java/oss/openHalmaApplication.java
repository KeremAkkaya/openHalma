package oss;

/**
 * Created by julian on 03.05.16.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class openHalmaApplication /*implements CommandLineRunner*/ {

    public static void main(String[] args) {
        //SpringApplication.run(openHalmaApplication.class, args); //not working because of headless error
        SpringApplicationBuilder builder = new SpringApplicationBuilder(openHalmaApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

}

