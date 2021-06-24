package friendsbook.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import friendsbook.jwt.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class FriendConfiguration extends WebSecurityConfigurerAdapter {
@Autowired
JwtFilter jwtFilter;
	@Autowired
FriendbookUserDetailService friendbookservice;
	@Autowired
	AuthenticationEntryPoint unauthentrypoint;
@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(friendbookservice);
	}

@Bean
	@Override
protected AuthenticationManager authenticationManager() throws Exception {
	// TODO Auto-generated method stub
	return super.authenticationManager();
}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests().antMatchers("/token","/signup","/img/**")
		.permitAll().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated().and().
		exceptionHandling().authenticationEntryPoint(unauthentrypoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	;
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	@Bean
	public PasswordEncoder passwordencoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	
}
