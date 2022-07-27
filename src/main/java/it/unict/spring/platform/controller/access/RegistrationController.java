package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.dto.user.OrganizationDTO;
import it.unict.spring.platform.dto.user.UserAccountDTO;
import it.unict.spring.platform.dto.user.UserRegisterDTO;
import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.exception.user.UserAccountAlreadyVerified;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.service.user.OrganizationService;
import it.unict.spring.platform.service.user.UserRegisterService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


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
    private static final Logger applogger = LoggerFactory.getLogger(RegistrationController.class);  
 
    
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
          model.addAttribute("fieldError","Errors occured, check your fields");
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          return new ModelAndView("public/access/registration/register");   
         }       
         try 
          {
            Organization organization = orgService.mapFromOrganization(orgdto);     
            UserRegister userreg=regService.mapFromUserRegister(userregdto);
            UserAccount user=userService.mapFromUserDTO(userdto, UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                                 userreg, organization);  
            
            userService.sendRegistrationMail(user, request.getRequestURL().toString());
            
          } 
          catch (MultipleUsersFoundException ex)
           {
            model.addAttribute("accountExists","This account already exists");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ModelAndView("public/access/registration/register");
           }
         
         response.setStatus(HttpServletResponse.SC_OK);
         model.addAttribute("confirmReg", "We sent an email. Check your inbox to complete the registration");
         return  new ModelAndView("public/access/login/signin");
     }
     
     
     @GetMapping(value={"/registerUser/registrationConfirm", "/confirmResendRegister/registrationConfirm" })
     public ModelAndView confirmRegistration(HttpServletRequest request,HttpServletResponse response,
                                      @RequestParam("token") String token, Model model)
     {
       try
       {
       if(userService.checkToken(token))       
       {
           model.addAttribute("tokenSuccess","You registration have been verified");
           response.setStatus(HttpServletResponse.SC_OK);
       }    
       else  
       {
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
          model.addAttribute("tokenFailed", "Registration failed");         
       }
       }
       catch(UserAccountAlreadyVerified exception)
       {
         model.addAttribute("tokenVerified", "Registration already verified");
         response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
       }
       return  new ModelAndView("public/access/login/signin");
     }
     
     
     @RequestMapping("resendRegister")
     public ModelAndView viewResendRegister(HttpServletRequest request,
                                      HttpServletResponse response,                                      
                                      Model model)
     {
          
          return new ModelAndView("public/access/registration/resendRegister");
     }
     
     @PostMapping("confirmResendRegister")
     public ModelAndView confirmResendRegister(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      Model model)
     {
         try
         {           
           List<UserAccount> user=userService.findByMailOrUsername(username);  
           if(user.size() == 1  && (!user.get(0).isEnabled()) && userService.comparePassword(user.get(0).getPassword(), password))
            {
             userService.sendRegistrationMail(user.get(0), request.getRequestURL().toString());
             response.setStatus(HttpServletResponse.SC_OK);
             model.addAttribute("confirmReg", "We sent an email. Check your inbox to finalize the registration");
             //return new ModelAndView("redirect:/public/api/access/registration/resendRegister?confirmReg");
            }
            else
             {
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               model.addAttribute("errorCredentials", "Invalid username and password or account already registered.");
               //return new ModelAndView("redirect:/public/api/access/registration/resendRegister?errorCredentials");
             }     
             
          }
          catch(Exception e)
           {
            applogger.error("Resend registration email error: "+ e.toString());
            model.addAttribute("generalError", "An error occurred");              
            //return new ModelAndView("redirect:/public/api/access/registration/resendRegister?error");
          }         
         return new ModelAndView("public/access/registration/resendRegister");       
     }
    
}
