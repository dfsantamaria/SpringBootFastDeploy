package it.unict.spring.platform.configuration.security.login;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService loginService;
    
    @Override   
    public void onApplicationEvent(AuthenticationSuccessEvent event)
    {
        CustomUserDetails user = (CustomUserDetails) event.getAuthentication().getPrincipal();
        UserLogin login = userService.findByMail(user.getMail()).get().getLogin();        
        if(login.getLastFailDate()!=null)
            loginService.resetLoginFail(login);
        
    }
}
