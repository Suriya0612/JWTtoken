package jwt.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jwt.spring.security.filter.JwtAuthFilter;
import jwt.spring.security.service.UserService;
@Configuration
@Profile("dev")
@EnableWebSecurity  //customize spring security for jwt
@EnableMethodSecurity
public class SecurityConfig {
	
	 @Autowired
	    private JwtAuthFilter authFilter; 
	
//	 @Autowired Environment env;
	 
	 @Bean   //user creation
	    public UserDetailsService userDetailsService() { 
	        return new UserService(); 
	    } 

	 @Bean   //configuring httpsecurity
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
	        return http.csrf().disable() 
	                .authorizeHttpRequests() 
	                .requestMatchers("/api/welcome", "/api/addNewUser", "/api/generateToken").permitAll() 
	                .and() 
	                .authorizeHttpRequests().requestMatchers("/api/user/**").authenticated() 
	                .and() 
	                .authorizeHttpRequests().requestMatchers("/api/admin/**").authenticated() 
	                .and() 
	                .sessionManagement() 
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
	                .and() 
	                .authenticationProvider(authenticationProvider()) 
	                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) 
	                .build(); 
	    } 

	  @Bean
	    public PasswordEncoder passwordEncoder() { 
	        return new BCryptPasswordEncoder(); 
	    } 
	  
	   @Bean
	    public AuthenticationProvider authenticationProvider() { 
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
	        authenticationProvider.setUserDetailsService(userDetailsService()); 
	        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
	        return authenticationProvider; 
	    } 
	   
	   @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
	        return config.getAuthenticationManager(); 
	    } 
	   
	   
//	   @Primary
//	   @Bean     //(name="userDataSource")
//	   @ConfigurationProperties(prefix="spring.datasource.first")
//		public DataSource userDataSource() {
//		   DriverManagerDataSource userdataSource = new DriverManagerDataSource();
//		    userdataSource.setDriverClassName(env.getProperty("org.postgresql.Driver"));
//		    userdataSource.setUrl(env.getProperty("jdbc:postgresql://localhost:5433/Jwttokendev"));
//		    userdataSource.setUsername(env.getProperty("postgres"));
//		    userdataSource.setPassword(env.getProperty("vijay"));
//		 	return userdataSource;
//		    return DataSourceBuilder.create().build();
//
//		 }
//	   @Bean    //(name="detailsDataSource")
//	   @ConfigurationProperties(prefix="spring.datasource.second")
//		public DataSource detailsDataSource() {
//		   DriverManagerDataSource detailsdataSource = new DriverManagerDataSource();
//		    detailsdataSource.setDriverClassName(env.getProperty("org.postgresql.Driver"));
//		    detailsdataSource.setUrl(env.getProperty("jdbc:postgresql://localhost:5433/Jwttoken"));
//		    detailsdataSource.setUsername(env.getProperty("postgres"));
//		    detailsdataSource.setPassword(env.getProperty("vijay"));
//		 	return detailsdataSource;
//		    return DataSourceBuilder.create().build();
//
//		}

}
