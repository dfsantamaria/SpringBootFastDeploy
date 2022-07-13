/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.configuration.security.authentication;

import it.unict.spring.application.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author danie
 */
@Controller
public class AuthenticationController
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
