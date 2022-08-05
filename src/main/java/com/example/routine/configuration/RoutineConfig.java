package com.example.routine.configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

@Configuration
public class RoutineConfig {
	/*
	 * https://www.javafixing.com/2021/10/fixed-autowired-gives-null-value-in.html
	 * 
	 * 
	 */
	@Bean
	public Validator validator(AutowireCapableBeanFactory beanFactory) {
	    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
	            .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))
	            .buildValidatorFactory();

	    return validatorFactory.getValidator();
	}
}
