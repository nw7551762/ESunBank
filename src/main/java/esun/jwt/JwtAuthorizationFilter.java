package esun.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import esun.user.User;
import esun.user.UserService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    final String authHeader = request.getHeader("Authorization");
	    final String jwt;
	    final String userName;
	    final String requestUri = request.getRequestURI();

	    if (isRegistRequest(requestUri)) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        // 重定向到登录页面
	        filterChain.doFilter(request, response);
	        return;
	    }

	    if (isAuthorizationHeaderValid(authHeader)) {
	        String[] headerParts = authHeader.split(" ");
	        if (headerParts.length == 2) {
	            jwt = headerParts[1];
	            userName = jwtService.extractUsername(jwt);
	            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	                User user = userService.findByUsername(userName).orElse(null);
	                if (user != null && jwtService.isTokenValid(jwt, user)) {
	                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                            user,
	                            null,
	                            user.getAuthorities());
	                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                    SecurityContextHolder.getContext().setAuthentication(authToken);
	                }
	            }else {
	            	
	            }
	        }
	    }

	    filterChain.doFilter(request, response);
	}

	private boolean isAuthorizationHeaderValid(String authHeader) {
	    return authHeader != null && authHeader.startsWith("Bearer ");
	}

	private boolean isRegistRequest(String requestUri) {
	    return requestUri.equals("/registerPage") || requestUri.equals("/api/demo/register");
	}



}
