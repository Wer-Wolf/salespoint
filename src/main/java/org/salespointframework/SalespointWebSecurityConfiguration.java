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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Basic Salespoint security configuration setting up the {@link AuthenticationManagerBuilder} to work with the
 * {@link UserDetailsService} implementation as well as the {@link PasswordEncoder} we provide.
 *
 * @author Oliver Gierke
 */
@Configuration
class SalespointWebSecurityConfiguration {

	@Autowired UserDetailsService userDetailsService;

	@Bean
	@ConditionalOnWebApplication
	SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

		return security
				.userDetailsService(userDetailsService)
				.authorizeHttpRequests().requestMatchers("/resources/**").permitAll()
				.and().build();
	}
}
