/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.controller.access;

import it.unict.spring.application.dto.user.OrganizationDTO;
import it.unict.spring.application.dto.user.UserAccountDTO;
import it.unict.spring.application.dto.user.UserRegisterDTO;
import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.persistence.model.user.UserRegister;
import it.unict.spring.application.service.user.OrganizationService;
import it.unict.spring.application.service.user.UserRegisterService;
import it.unict.spring.application.service.user.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author danie
 */
@Controller
@RequestMapping("/public/api/access/registration")
public class RegistrationController
{
    @Autowired
    UserService userService;
    @Autowired
    OrganizationService orgService;
    @Autowired
    UserRegisterService regService;
    
    
     @RequestMapping("register")
     public ModelAndView viewRegister(HttpServletRequest request,
                                      HttpServletResponse response,
                                @ModelAttribute("userregdto") UserRegisterDTO userregdto,
                                BindingResult userRegBindResult,
                                @ModelAttribute("userdto") UserAccountDTO userdto, 
                                BindingResult userBindResult,
                                @ModelAttribute("orgdto") OrganizationDTO orgdto, 
                                BindingResult orgBindResult,
                                Model model)
     {    
         model.addAttribute("userregdto", userregdto);
         model.addAttribute("userdto", userdto);  
         model.addAttribute("orgdto", orgdto);
         return new ModelAndView("public/access/registration/register");
     }
     
     
     
     @RequestMapping(value="registerUser", method = RequestMethod.POST)
     public ModelAndView registerUser(HttpServletRequest request,
                                      HttpServletResponse response,
                                  @ModelAttribute("userregdto") UserRegisterDTO userregdto,
                                  BindingResult userRegBindResult,
                                  @ModelAttribute("userdto") @Valid UserAccountDTO userdto,
                                  BindingResult userBindResult,
                                  @ModelAttribute("orgdto") @Valid OrganizationDTO orgdto,
                                  BindingResult orgBindResult,                                 
                                  Model model)
     {        
         if(userBindResult.hasErrors() || orgBindResult.hasErrors() || userRegBindResult.hasErrors())
         {  
          model.addAttribute("errorMessage","Errors occured, check your fields");
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          return new ModelAndView("public/access/registration/register");   
         }
         Organization organization = orgService.mapFromOrganization(orgdto);
         UserRegister userreg=regService.mapFromUserRegister(userregdto);
         try 
          {
            UserAccount user=userService.mapFromUserDTO(userdto, userreg, organization);           
            userService.sendRegistrationMail(user);
          } 
          catch (MultipleUsersFoundException ex)
           {
            model.addAttribute("errorMessage","Account already exists");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ModelAndView("public/access/registration/register");
           }
         
         return  new ModelAndView("redirect:/");
     }
    
}
