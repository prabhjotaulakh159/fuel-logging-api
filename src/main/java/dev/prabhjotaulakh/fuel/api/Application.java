package dev.prabhjotaulakh.fuel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * REST-API entrypoint
 * @author Prabhjot Aulakh
 */
@SpringBootApplication
public class Application {
    /**
     * Runs the server
     * @param args Command line arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
