/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.controller.access;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author danie
 */

@Controller
@RequestMapping("/public/api/access/login")
public class SigninController
{         
     @RequestMapping("signin")
     public ModelAndView viewlogin(HttpServletRequest request, Model model)
     {              
        return new ModelAndView("public/access/login/signin");
     }      
}

