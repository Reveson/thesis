package siemieniuk.thesis.feedservice.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfig {

	private static final ExecutorService feedExecutorService =
		Executors.newFixedThreadPool(5);

	@Bean
	public ExecutorService feedExecutorService() {
		return feedExecutorService;
	}
}
