package it.unict.spring.platform.configuration.security.authentication;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.service.security.CustomUserDetailsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
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
    
    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviders)
    {
    return new ProviderManager(authenticationProviders);
    }
    
   
}
