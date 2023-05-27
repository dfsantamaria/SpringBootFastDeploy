package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.dto.user.LoginDTO;
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
import it.unict.spring.platform.utility.user.ServerUtils;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



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
    @Autowired
    ServerUtils serverUtils;
    
    private static final Logger applogger = LoggerFactory.getLogger(RegistrationController.class);  
 
         
     @GetMapping(value="test")
     public String testUtils(HttpServletRequest request)
     {
       return serverUtils.getContextURL(request);
     }
    
     
     @PostMapping(value="registerUser", consumes = {"application/json"})
     public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserDTO registration, HttpServletRequest request)
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
            JSONObject content=new JSONObject();
            content.put("url", serverUtils.getContextURL(request)+"/public/api/access/registration/registerUser/registrationConfirm");
            userService.sendRegistrationMail(user, new JSONObject());
            if(registration.getUser().getRole() == AuthManager.getStaffPriority())
            {  
              JSONObject admincontent=new JSONObject();
              admincontent.put("url", serverUtils.getContextURL(request)+"/auth/api/admin/upgradeUserRoleAtStaff");
              userService.sendEnableStaffRoleMail(user, content);
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
     
          
     
     @PostMapping("confirmResendRegister")
     public ResponseEntity<String> confirmResendRegister(@Valid @RequestBody LoginDTO logindto)
     {
         JSONObject obj=new JSONObject(); 
         try
         {           
           List<UserAccount> user=userService.findByMailOrUsername(logindto.getUsername());  
           if(user.size() == 1  && (!user.get(0).isEnabled()) && userService.comparePassword(user.get(0).getPassword(), logindto.getPassword()))
            {
             //userService.sendRegistrationMail(user.get(0), request.getRequestURL().toString());
             obj.put("status", "success");                     
             return new ResponseEntity<>(obj.toString(), HttpStatus.OK);              
            }
            else
             {
               obj.put("status", "failed");
               obj.put("error", "invalid credentials");
               return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);               
             }                  
          }
          catch(Exception e)
           {
            applogger.error("Resend registration email error: "+ e.toString());
            obj.put("status", "failed");
            obj.put("error", "internal error");
            return new ResponseEntity<>(obj.toString(), HttpStatus.INTERNAL_SERVER_ERROR);               
           }         
           
     }
    
}
