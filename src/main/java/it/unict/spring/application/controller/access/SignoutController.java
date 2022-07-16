/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.controller.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth/api/access/login")
public class SignoutController implements LogoutHandler
{        
     @RequestMapping("signout")
     public void viewlogout(HttpServletRequest request, HttpServletResponse response, Model model)
     {    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        this.logout(request, response, authentication);        
     }  

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {        
        if (authentication != null)
        {    
           new SecurityContextLogoutHandler().logout(request, response, authentication);
        }     
    }
}
