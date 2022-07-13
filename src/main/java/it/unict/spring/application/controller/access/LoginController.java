/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.controller.access;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author danie
 */

@Controller
@RequestMapping("/public/access")
public class LoginController
{ 
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
}

