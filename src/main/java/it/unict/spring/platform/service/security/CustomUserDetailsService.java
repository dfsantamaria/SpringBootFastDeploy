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
import it.unict.spring.platform.exception.user.TooManyLoginAttemptsException;
import it.unict.spring.platform.exception.user.UserCredentialsExpiredException;
import it.unict.spring.platform.exception.user.UserNotEnabledException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;


@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService loginService;

    @Override    
    @Transactional
    public UserDetails loadUserByUsername(String username)
    {
        List<UserAccount> users = new ArrayList<>();
        Optional<UserAccount> user=userService.findByUsername(username);
        if(!user.isEmpty())
             users.add(user.get());
        user=userService.findByMail(username);
        if(!user.isEmpty())
            users.add(user.get());
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
        UserLogin login = users.get(0).getLogin();
        if(login.getFailCount() + 1 > 3 && LocalDateTime.now().isBefore(login.getLastFailDate().toLocalDateTime().plusMinutes(60)) )
            throw new TooManyLoginAttemptsException(username);  
        return new CustomUserDetails(users.get(0));
    }
}
