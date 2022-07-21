package es.agenda.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http
		.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        	.authorizeRequests()
        	.antMatchers("/api/web/**").hasRole("USUARIO")
        	.antMatchers("/api/admin/**").hasRole("ADMIN")
        	.antMatchers("/api/login").permitAll()
        	/*
        	.and()
        	.formLogin()
        	.loginPage("/login")
        	.defaultSuccessUrl("/crearToken", true)
        	.failureUrl("/loginError")
        	*/
        .and()
        	.headers()
        	.frameOptions()
        	.disable()
		.and()
		.cors().configurationSource(c -> {
			
			CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
			configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
			
		    return configuration;
		})
		.and().csrf().disable();
	}
}