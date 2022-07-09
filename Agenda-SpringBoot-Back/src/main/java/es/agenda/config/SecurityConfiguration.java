package es.agenda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
 
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
        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
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
		.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
		.and().csrf().disable();
	}
	
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//	    return super.authenticationManagerBean();
//	}
}