package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.dto.user.RegisterUserDTO;
import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.exception.user.UserAccountAlreadyVerified;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.service.user.OrganizationService;
import it.unict.spring.platform.service.user.UserRegisterService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.AuthManager;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



@RestController
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
 
         
    
     
     @PostMapping(value="registerUser", consumes = {"application/json"})
     public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserDTO registration)
     {        
         JSONObject obj=new JSONObject();     
         try 
          {
            
            Organization organization = orgService.mapFromOrganization(registration.getOrganization());     
            UserRegister userreg=regService.mapFromUserRegister(registration.getRegister());
            UserAccount user=userService.mapFromUserDTO(registration.getUser(), UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                                 userreg, organization);  
            userService.createLoginInfo(user);
            userService.sendRegistrationMail(user, new JSONObject());
            if(registration.getUser().getRole() == AuthManager.getStaffPriority())
            {                
              userService.sendEnableStaffRoleMail(user, new JSONObject());
            }
          } 
          catch (MultipleUsersFoundException ex)
           {
            obj.put("status", "failed");
            obj.put("error", "Account already exists");            
            return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);
           }
          catch (Exception ex)
          {
            obj.put("status", "failed");
            obj.put("error", "Internal error");            
            return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);
          }
         
           obj.put("status", "success");                     
           return new ResponseEntity<>(obj.toString(), HttpStatus.OK);         
     }
     
     
     @GetMapping(value={"/registerUser/registrationConfirm", "/confirmResendRegister/registrationConfirm" })
     public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token)
     {
       JSONObject obj=new JSONObject();  
       try
       {
       if(userService.verifyRegistrationToken(token))       
       {
           obj.put("status", "success");                     
           return new ResponseEntity<>(obj.toString(), HttpStatus.OK);   
       }    
       else  
       {
          obj.put("status", "failed");
          obj.put("error", "token failed");
          return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);            
       }
       }
       catch(UserAccountAlreadyVerified exception)
       {
         obj.put("status", "failed");
         obj.put("error", "token invalid or expired");
         return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST); 
       }
       
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
             //userService.sendRegistrationMail(user.get(0), request.getRequestURL().toString());
             response.setStatus(HttpServletResponse.SC_OK);
             model.addAttribute("confirmReg", "We sent an email. Check your inbox to finalize the registration");
            }
            else
             {
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               model.addAttribute("errorCredentials", "Invalid username and password or account already registered.");
             }     
             
          }
          catch(Exception e)
           {
            applogger.error("Resend registration email error: "+ e.toString());
            model.addAttribute("generalError", "An error occurred");              
           }         
         return new ModelAndView("public/access/registration/resendRegister");       
     }
    
}
