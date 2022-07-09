package es.agenda.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import es.agenda.util.JWTUtils;

@Component
public class JwtFilter extends OncePerRequestFilter   {
    
	private static final Logger LOG = LoggerFactory.getLogger(JwtFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String jwt = JWTUtils.existeToken(request);
	    
		if(jwt == null) {
			
			filterChain.doFilter(request, response);
	    	
			LOG.info("No hay token");
			
	    	return;
	    }
	              
		if(JWTUtils.validarToken(jwt)){
			
			Authentication authentication = JWTUtils.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		
		}else {
			
			LOG.info("El token no es válido");
		}
		
		filterChain.doFilter(request, response);	
		
	}
}

