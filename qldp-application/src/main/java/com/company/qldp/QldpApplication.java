package com.company.qldp;

import com.company.qldp.oauth.main.config.ResourceServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ResourceServerConfiguration.class})
public class QldpApplication {

	public static void main(String[] args) {
		SpringApplication.run(QldpApplication.class, args);
	}

}
