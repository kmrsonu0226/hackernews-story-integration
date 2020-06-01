package com.story.integration;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StoryIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryIntegrationApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		builder.setConnectTimeout(Duration.ofSeconds(60));
		builder.setReadTimeout(Duration.ofSeconds(60));
		return builder.build();
	}

}
