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
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
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
        Class exceptionClass = exception.getClass();
        if(exceptionClass == UserNotEnabledException.class)
            getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorEnabled"); 
        else if(exceptionClass == UserAccountExpiredException.class)
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorExpired"); 
        else if(exceptionClass == UserAccountLockedException.class)
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorLocked"); 
        else if(exceptionClass == UserAccountExpiredException.class)
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorCredentials"); 
        else if(exceptionClass == TooManyLoginAttemptsException.class)
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorAttempts"); 
        else if(exceptionClass == BadCredentialsException.class)  
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?errorLogin");
        else
           getRedirectStrategy().sendRedirect(request, response, "/public/api/access/login/signin?error");        
    }
}
