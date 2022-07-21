package it.unict.spring.platform.configuration.security.authentication;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AuthenticationConfiguration
{
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder getPasswordEncoder;
    
    @Bean
    public DaoAuthenticationProvider authProvider()
    {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(getPasswordEncoder);
    return authProvider;
    }
    
   
}
