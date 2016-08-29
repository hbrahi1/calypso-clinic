package com.calypso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.calypso.util.XMLConverter;

@Configuration
public class UtilitiesConfig
{

	@Bean
	public XMLConverter xmlConverter() {
		return new XMLConverter();
	}
	
}
