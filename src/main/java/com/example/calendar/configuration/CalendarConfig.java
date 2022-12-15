package com.example.calendar.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
@Configuration
public class CalendarConfig {
	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
