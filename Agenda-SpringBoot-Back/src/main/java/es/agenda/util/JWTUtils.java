package es.agenda.util;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public final class JWTUtils {

	private static final Logger LOG = LoggerFactory.getLogger(JWTUtils.class);
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	
	private static final String BEARER = "Bearer ";
	
	private static final String ID = "ID";
	
	private static final String ROLES = "ROLES";
	
	private static String keyString = "ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI=";
	
	private static byte[] keyBytes = Decoders.BASE64.decode(keyString);
	
	private static Key key = Keys.hmacShaKeyFor(keyBytes);
	
	public static String crearToken(Long id, String usuario, String roles) {
		
		return Jwts.builder()
				.setSubject(usuario)
				.claim(ID, id)
				.claim(ROLES, roles)
    	        .signWith(key, SignatureAlgorithm.HS512)
    	        //.setExpiration(validity)
    	        .compact();
	}
	
	public static Authentication getAuthentication(String token) {
        
		Claims claims = Jwts.parserBuilder()
           .setSigningKey(key).build()
           .parseClaimsJws(token)
           .getBody();

        Collection<? extends GrantedAuthority> authorities =
           Arrays.stream(claims.get(ROLES).toString().split(","))
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

        User user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }
	
	public static boolean validarToken(String token) {
        
		try {
        	
        	Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        	
        	return true;
        
		} catch (Exception e) {
			
			LOG.error("Error validando token {} ", e);
        }
        
        return false;
    }
	
	public static String existeToken(HttpServletRequest request) {
		
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
        
        	return bearerToken.substring(7);
        }
        
        return null;
     }

	public static Long getIdToken(HttpServletRequest request) {
		
		String token = existeToken(request);
		
		if(token != null) {
			
			Claims claims = Jwts.parserBuilder()
			           .setSigningKey(key).build()
			           .parseClaimsJws(token)
			           .getBody();
			
			return Long.valueOf((Integer)claims.get(ID));
		}
		
		return null;
	}
}
