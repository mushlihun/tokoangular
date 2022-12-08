package com.tokorestapi.angular.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tokorestapi.angular.services.email.EmailService;
import com.tokorestapi.angular.services.email.SmtpEmailService;

@Configuration
public class EmailConfiguration {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
