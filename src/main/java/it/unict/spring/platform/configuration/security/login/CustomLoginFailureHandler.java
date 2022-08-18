package it.unict.spring.platform.configuration.security.login;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler
{

    @Autowired
    UserService userService;
    @Autowired
    UserLoginService loginService;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException
    {      
        super.setDefaultFailureUrl("/public/api/access/login/signin?error");
        if(exception.getMessage().startsWith("Too many login attempts"))
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorAttempts");                 
        else if(exception.getMessage().startsWith("Registration not completed"))
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorEnabled"); 
        else if(exception.getMessage().startsWith("User account is expired"))
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorExpired"); 
        else if(exception.getMessage().startsWith("User account is locked"))
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorLocked"); 
        else if(exception.getMessage().startsWith("User credentials are expired"))
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorCredentials"); 
        else  if(exception.getMessage().startsWith("Bad credentials"))
        {            
            String username=request.getParameter("username");            
            List<UserAccount> accounts = userService.findByMailOrUsername(username);
            if(!accounts.isEmpty())
             {
             Long id = accounts.get(0).getId();
             UserLogin userLogin = loginService.findById(id).get();            
             loginService.updateLoginFail(userLogin);             
             if(userLogin.getFailCount()  > 3 && LocalDateTime.now().isBefore(userLogin.getLastFailDate().toLocalDateTime().plusMinutes(60)) )
             {
                 userService.setSuspended(accounts.get(0), true);
                 getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorAttempts");
             }       
             else
                 getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorLogin");      
             }
            else 
                getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorLogin");
      
        }  
       
    }
}
