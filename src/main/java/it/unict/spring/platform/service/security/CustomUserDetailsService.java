package it.unict.spring.platform.service.security;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.exception.user.UserAccountExpiredException;
import it.unict.spring.platform.exception.user.UserAccountLockedException;
import it.unict.spring.platform.exception.user.UserCredentialsExpiredException;
import it.unict.spring.platform.exception.user.UserNotEnabledException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.repository.user.UserRepository;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override    
    public UserDetails loadUserByUsername(String username)
    {
        List<UserAccount> users = new ArrayList<>();
        users.addAll(userRepository.findByUsername(username));
        users.addAll(userRepository.findByMail(username));        
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
