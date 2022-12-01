/*
 * Copyright 2017-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.salespointframework;

import java.util.HashMap;

import org.salespointframework.inventory.LineItemFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Application configuration for Salespoint.
 *
 * @author Oliver Gierke
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { //
		@Filter(Configuration.class), //
		@Filter(value = TypeExcludeFilter.class, type = FilterType.CUSTOM) //
})
@EntityScan
@EnableMethodSecurity
@ConfigurationPropertiesScan
@PropertySource("classpath:salespoint.properties")
public class Salespoint {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public LineItemFilter inventoryLineItemFilter() {
		return LineItemFilter.handleAll();
	}

	/**
	 * Needed until https://github.com/spring-projects/spring-boot/issues/12194 and
	 * https://github.com/spring-projects/spring-boot/issues/16747 are fixed.
	 *
	 * @author Oliver Gierke
	 */
	static class RequiredPropertiesPostProcessor implements EnvironmentPostProcessor {

		/*
		 * (non-Javadoc)
		 * @see org.springframework.boot.env.EnvironmentPostProcessor#postProcessEnvironment(org.springframework.core.env.ConfigurableEnvironment, org.springframework.boot.SpringApplication)
		 */
		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

			var sources = environment.getPropertySources();

			var properties = new HashMap<String, Object>();
			properties.put("spring.aop.proxy-target-class", false);
			properties.put("spring.datasource.generate-unique-name", true);
			properties.put("spring.jpa.open-in-view", true);
			properties.put("spring.jpa.properties.hibernate.auto_quote_keyword", true);

			sources.addFirst(new MapPropertySource("salespointDefaults", properties));
		}
	}
}
