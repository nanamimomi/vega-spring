package com.uvic.venus;

import com.uvic.venus.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties(StorageProperties.class)
@PropertySource("classpath:application-dev.properties")
public class VenusApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenusApplication.class, args);
	}

}
