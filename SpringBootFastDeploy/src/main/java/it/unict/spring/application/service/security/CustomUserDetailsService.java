/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.service.security;

import it.unict.spring.application.persistence.model.user.Users;
import it.unict.spring.application.persistence.repository.user.UserRepository;
import it.unict.spring.application.utility.user.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author danie
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) 
    {
        List<Users> users = new ArrayList<>();
        users.addAll(userRepository.findByUsername(username));
        users.addAll(userRepository.findByMail(username));        
        if (users.isEmpty())
        {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(users.get(0));
    }
}
