package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Creates the OrlandoLendIT Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = "app")
public class Application {
    /**
     * The main method is the entry point for the application. It will be used to start the Spring Boot application.
     *
     * @param args command line arguments supplied at startup
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}