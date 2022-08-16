package it.unict.spring.platform.configuration.security.login;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.exception.user.TooManyLoginAttemptsException;
import it.unict.spring.platform.exception.user.UserAccountExpiredException;
import it.unict.spring.platform.exception.user.UserAccountLockedException;
import it.unict.spring.platform.exception.user.UserNotEnabledException;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
        else
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorLogin");
    }
}
