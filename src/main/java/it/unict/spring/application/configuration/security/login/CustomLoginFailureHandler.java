/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.configuration.security.login;

import it.unict.spring.application.exception.user.UserNotEnabledException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{

   
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException
    {        
        if(exception.getMessage().startsWith("User is not enabled"))
           getRedirectStrategy().sendRedirect(request, response, "/public/access/signin?errorEnabled"); 
        else if(exception.getMessage().startsWith("User account is expired"))
           getRedirectStrategy().sendRedirect(request, response, "/public/access/signin?errorExpired"); 
        else if(exception.getMessage().startsWith("User account is locked"))
           getRedirectStrategy().sendRedirect(request, response, "/public/access/signin?errorLocked"); 
        else if(exception.getMessage().startsWith("User credentials are expired"))
           getRedirectStrategy().sendRedirect(request, response, "/public/access/signin?errorCredentials"); 
        else
           getRedirectStrategy().sendRedirect(request, response, "/public/access/signin?error");
    }
}
