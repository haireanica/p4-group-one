/**
 * The main application class for the OrlandoLendIT application.
 */
package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    /**
     * The main method is the entry point for the application. It will be used to start the Spring Boot application.
     * It will also be used to configure the application context, and to set up the application
     * environment.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}