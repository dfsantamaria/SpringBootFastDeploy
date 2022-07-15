package it.unict.spring.application.configuration.security.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author danie
 */
@Configuration
public class PasswordEncoderConfiguration
{
    @Bean
    PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
   } 
}
