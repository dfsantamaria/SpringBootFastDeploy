/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.platform.controller.access;


import it.unict.spring.platform.dto.user.AccountPasswordDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/public/api/access/recover")
public class RecoverAccessController
{    
    @Autowired
    UserService userService;
    
    @RequestMapping("viewRecoverPassword")
    public ModelAndView recoverPasswordView(HttpServletRequest request,
                                            HttpServletResponse response,                                          
                                            Model model)
    {
         //model.addAttribute("requestNewPassword", "We sent an email to reset your password");
       //response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
       System.out.println("mail");
       return  new ModelAndView("public/access/recover/recoverPassword");
        
    }
    @RequestMapping("sendRecoverPassword")
    public ModelAndView sendRecoverPasswordView(HttpServletRequest request,
                                            HttpServletResponse response,  
                                            @RequestParam("mail") String mail,
                                            Model model)
    {
       List<UserAccount> users = userService.findByMail(mail); 
       model.addAttribute("requestNewPassword", "We sent an email to reset your password");
       response.setStatus(HttpServletResponse.SC_OK);
       if(!users.isEmpty())
       {
         userService.sendRecoverPasswordMail(users.get(0), request.getRequestURL().toString());
       }       
       return  new ModelAndView("public/access/recover/recoverPassword");        
    }
    
    @GetMapping(value={"/sendRecoverPassword/checkResetPassword"})
     public ModelAndView confirmRegistration(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam("token") String token, Model model)
     {
      
         model.addAttribute("token", token);
         return new ModelAndView("public/access/recover/changePassword");
     }
     
    @PostMapping(value="changePassword")
    public ModelAndView changePassword(HttpServletRequest request,
                                       HttpServletResponse response, 
                                       @RequestParam("token") String token,
                                       @ModelAttribute("passdto") @Valid AccountPasswordDTO passdto,
                                       BindingResult passBindResult,
                                       Model model)
    {  
       if(passBindResult.hasErrors())
       {               
           model.addAttribute("passError", "Check the password fields");
           model.addAttribute("token", token);
           return new ModelAndView("public/access/recover/changePassword");
       }
       System.out.println(token);
       if(userService.verifyPasswordChangedToken(token, passdto.getPassword()))
       {               
          model.addAttribute("changedPassword", "Password correctly reset");
          return new ModelAndView("public/access/login/signin");
       }  
       else
       {
         model.addAttribute("passError", "Link not valid");
         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         return new ModelAndView("public/access/recover/changePassword");
       }
      
    }
    
}