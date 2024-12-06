package com.example.vipa;

import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import com.example.vipa.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class VipaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VipaApplication.class, args);
	}

}
