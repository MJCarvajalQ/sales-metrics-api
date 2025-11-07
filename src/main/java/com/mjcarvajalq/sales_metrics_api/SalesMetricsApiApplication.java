package com.mjcarvajalq.sales_metrics_api;

import com.mjcarvajalq.sales_metrics_api.model.ActionType;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.model.User;
import com.mjcarvajalq.sales_metrics_api.repositories.OutreachActionRepository;
import com.mjcarvajalq.sales_metrics_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@SpringBootApplication
public class SalesMetricsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesMetricsApiApplication.class, args);
	}

	@Bean
	@Profile("!prod")  // Only run in non-production environments
	@ConditionalOnProperty(name = "app.data.seeding.enabled", havingValue = "true", matchIfMissing = true)
	public CommandLineRunner dataLoader(
			UserRepository userRepo,
			OutreachActionRepository actionRepo,
			@Value("${app.data.seeding.enabled:true}") boolean seedingEnabled
	) {
		return args -> {
			if (!seedingEnabled) {
				System.out.println("Data seeding is disabled.");
				return;
			}

			// Check if data already exists (idempotent check)
			if (userRepo.count() > 0) {
				System.out.println("Data already exists. Skipping seeding.");
				return;
			}

			// Seed users
			User user1 = new User();
			user1.setName("Majo");
			user1.setEmail("majo@example.com");

			User user2 = new User();
			user2.setName("Pedro");
			user2.setEmail("pedro@example.com");

			userRepo.save(user1);
			userRepo.save(user2);

			// Seed actions
			OutreachAction action1 = new OutreachAction();
			action1.setUser(user1);
			action1.setType(ActionType.EMAIL);
			action1.setDateTime(LocalDateTime.now().minusDays(1));
			action1.setNotes("Sent introduction email");

			OutreachAction action2 = new OutreachAction();
			action2.setUser(user1);
			action2.setType(ActionType.MEETING);
			action2.setDateTime(LocalDateTime.now());
			action2.setNotes("Scheduled Zoom meeting");

			actionRepo.save(action1);
			actionRepo.save(action2);

			System.out.println("Test users and actions seeded successfully.");
		};
	}

}
