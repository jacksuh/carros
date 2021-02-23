package com.jackson.carros.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;

import com.jackson.carros.controller.modelo.Carro;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfigurations {

	@Bean
	public Docket forumApi(){
	return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.jackson.carros"))
		.paths(PathSelectors.ant("/**"))
		.build()
		.ignoredParameterTypes(Carro.class)
		.globalOperationParameters(Arrays.asList(
				new ParameterBuilder()
				.name("Authorization")
				.description("Header para token JWT")
				.modelRef(new ModelRef("String"))
				.parameterType("header")
				.required(false)
				.build()));
	}
}
