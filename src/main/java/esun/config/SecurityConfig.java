package esun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import esun.jwt.JwtAuthorizationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 @Autowired
	 private JwtAuthorizationFilter jwtAuthorizationFilter;
	 @Autowired
	 private AuthenticationProvider authenticationProvider;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/view/**").permitAll()
		.antMatchers("/api/demo/**").permitAll()
		.antMatchers("/registerPage").permitAll()
		.antMatchers("/post/**", "/auth/**" ).authenticated() // 需要驗證的路徑
		.anyRequest().permitAll() // 其他路徑不需要驗證
		.and()
		.formLogin()
		.loginPage("/view/loginPage")
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    		
    	return http.build();
    }
}
