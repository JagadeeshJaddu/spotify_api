package com.api.spotify_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com")
@EnableJpaRepositories(basePackages = "com")
@EntityScan("com.*")
public class SpotifyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyApiApplication.class, args);
	}

}
