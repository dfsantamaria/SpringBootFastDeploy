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
import javax.transaction.Transactional;
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
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException
    {     
                
        if(exception.getMessage().startsWith("Registration not completed"))           
           response.sendError(HttpServletResponse.SC_FORBIDDEN, "Registration not completed");
        else if(exception.getMessage().startsWith("Bad credentials"))
           response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bad credentials");
        else if(exception.getMessage().startsWith("User account is locked"))
           response.sendError(HttpServletResponse.SC_FORBIDDEN, "User account is locked"); 
        else if(exception.getMessage().startsWith("User credentials are expired"))
           response.sendError(HttpServletResponse.SC_FORBIDDEN, "User credentials are expired"); 
        else  if(exception.getMessage().startsWith("Too many login attempts"))
        {            
            String username=request.getParameter("username");    
            
            List<UserAccount> accounts = userService.findByMailOrUsername(username);
            if(!accounts.isEmpty())
             {
             Long id = accounts.get(0).getId();
             UserLogin userLogin = loginService.findById(id).get();            
             loginService.updateLoginFailDate(userLogin);           
             //System.out.println(username + " "+userLogin.getFailCount()+ " "+LocalDateTime.now().isBefore(userLogin.getLastFailDate().toLocalDateTime().plusMinutes(60)));
             if(userLogin.getFailCount()  > 3 && LocalDateTime.now().isBefore(userLogin.getLastFailDate().toLocalDateTime().plusMinutes(60)) )
             {
                 userService.setSuspended(accounts.get(0), true);
                 response.sendError(HttpServletResponse.SC_FORBIDDEN, "To many login attemps");
             }       
             else
                 response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error Login");      
             }
            else                 
               response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error Login ");
      
        }  
       
    }
}
