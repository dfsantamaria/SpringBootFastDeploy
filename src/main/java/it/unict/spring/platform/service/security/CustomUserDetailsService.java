package it.unict.spring.platform.service.security;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.exception.user.UserAccountExpiredException;
import it.unict.spring.platform.exception.user.UserAccountLockedException;
import it.unict.spring.platform.exception.user.UserCredentialsExpiredException;
import it.unict.spring.platform.exception.user.UserNotEnabledException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.repository.user.UserRepository;
import it.unict.spring.platform.utility.user.CustomUserDetails;


@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override    
    @Transactional
    public UserDetails loadUserByUsername(String username)
    {
        List<UserAccount> users = new ArrayList<>();
        users.addAll(userRepository.findAllByUsername(username));
        users.addAll(userRepository.findAllByMail(username));        
        if (users.isEmpty())       
           throw new UsernameNotFoundException(username);                
        if (!users.get(0).isEnabled())
           throw new UserNotEnabledException(username);
        if(!users.get(0).isAccountNonExpired())        
            throw new UserAccountExpiredException(username);        
        if(!users.get(0).isAccountNonLocked())
            throw new UserAccountLockedException(username);
        if(!users.get(0).isCredentialsNonExpired())
            throw new UserCredentialsExpiredException(username);       
        return new CustomUserDetails(users.get(0));
    }
}
