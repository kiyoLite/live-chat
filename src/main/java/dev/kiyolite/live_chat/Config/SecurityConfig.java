package dev.kiyolite.live_chat.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author soyky
 */
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled = true , jsr250Enabled = true)
public class SecurityConfig {
    
    
    @Bean
    public SecurityFilterChain httpConfig(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth ->
                auth.anyRequest().permitAll()
        
        );
        
        return http.build();
    }
}
