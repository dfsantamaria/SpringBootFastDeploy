package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.dto.user.AccountPasswordDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import java.util.concurrent.TimeUnit;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/public/api/access/recover")
public class RecoverAccessController
{    
    @Autowired
    UserService userService;
    
    private static final Logger applogger = LoggerFactory.getLogger(RecoverAccessController.class);  
    
    @RequestMapping("viewRecoverPassword")
    public ModelAndView recoverPasswordView(HttpServletRequest request,
                                            HttpServletResponse response, 
                                            Authentication authentication,
                                            Model model)
    { 
       if(authentication!=null) 
            return new ModelAndView("redirect:/auth/api/all/accountView");
       return  new ModelAndView("public/access/recover/recoverPassword");        
    }
    
    
    @RequestMapping("sendRecoverPassword")
    public ModelAndView sendRecoverPasswordView(HttpServletRequest request,
                                            HttpServletResponse response,  
                                            @RequestParam("mail") String mail,
                                            Model model) 
    {
       Optional<UserAccount> users = userService.findByMail(mail); 
       model.addAttribute("requestNewPassword", "We sent an email to reset your password");
       response.setStatus(HttpServletResponse.SC_OK);
       if(!users.isEmpty())
       {
         userService.sendRecoverPasswordMail(users.get(), request.getRequestURL().toString());
       }
       else
       {  
           try 
           {
           //Simulate sending mail
           TimeUnit.SECONDS.sleep((long) (2+(Math.random()*3)));
           }
           catch (InterruptedException ex)
           {
               applogger.error(ex.toString());
           }
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