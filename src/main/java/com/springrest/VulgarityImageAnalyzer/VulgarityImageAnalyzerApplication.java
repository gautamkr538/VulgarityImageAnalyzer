package com.springrest.VulgarityImageAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
=======
>>>>>>> 3ea20124ebf6dc2a3b63569bf9754ff5747e0939

@SpringBootApplication
public class VulgarityImageAnalyzerApplication {

<<<<<<< HEAD
    public static void main(String[] args) {
        SpringApplication.run(VulgarityImageAnalyzerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
=======
	public static void main(String[] args) {
		SpringApplication.run(VulgarityImageAnalyzerApplication.class, args);
	}

>>>>>>> 3ea20124ebf6dc2a3b63569bf9754ff5747e0939
}
