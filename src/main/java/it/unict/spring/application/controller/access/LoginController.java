/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.controller.access;

import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.service.user.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author danie
 */

@Controller
@RequestMapping("/public/api/access")
public class LoginController
{ 
    @Autowired
    UserService userService;
    
     @RequestMapping("signin")
     public String viewlogin(HttpServletRequest request, Model model)
     {              
        return "public/access/signin";
     }  
     
     @RequestMapping("signout")
     public String viewlogout(HttpServletRequest request, Model model)
     {              
        return "public/access/signin";
     }  
     
     @RequestMapping("register")
     public String viewRegister(HttpServletRequest request, Model model, UserAccount user)
     {         
         model.addAttribute("user", user);       
         return "public/access/register";
     }
     
     @RequestMapping(value="registerUser", method = RequestMethod.POST)
     public String registerUser(@ModelAttribute("user") UserAccount user, Model model)
     {         
         userService.save(user);
         return "public/home";
     }
}

