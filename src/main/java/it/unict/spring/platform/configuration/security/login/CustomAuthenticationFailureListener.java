package it.unict.spring.platform.configuration.security.login;


import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService loginService;
    
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event)
    {
        String userName = event.getAuthentication().getPrincipal().toString();
        //Object credentials = event.getAuthentication().getCredentials();
        List<UserAccount> accounts = userService.findByMailOrUsername(userName);
        if(!accounts.isEmpty())
           {
            Long id = accounts.get(0).getId();
            UserLogin userLogin = loginService.findById(id).get();            
            loginService.updateLoginFail(userLogin);
           }
        
    }
}