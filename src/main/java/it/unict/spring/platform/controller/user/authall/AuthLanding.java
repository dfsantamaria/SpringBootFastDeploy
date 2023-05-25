package it.unict.spring.platform.controller.user.authall;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.dto.user.AccountPasswordDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserRegisterService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.AuthManager;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/auth/api/all")
/*
* Manage the landing page of logged users
*/
public class AuthLanding
{
    @Autowired
    UserService userService;
    @Autowired
    UserRegisterService regService;
    
        
    @PostMapping(value="updatePassword", consumes = {"application/json"})
    public ResponseEntity<String> updatePassword(@Valid @RequestBody  AccountPasswordDTO password, @AuthenticationPrincipal CustomUserDetails user)
        {
         JSONObject obj=new JSONObject();
         UserAccount account=userService.findByMail(user.getMail()).get();         
         if(userService.comparePassword(account.getPassword(), password.getOldpassword()))
           {
                account.setPassword(userService.encodePassword(password.getPassword()));
                userService.save(account);   
                userService.sendNotificationMail(user.getId(), "Web Portal", "Your account's password has been changed. "
                        + "For any enquires contact an administrator.");
                obj.put("status","success");
                return new ResponseEntity<>(obj.toString(),HttpStatus.OK);
           }
              else
              {
                obj.put("status","failed");
                obj.put("errors", new JSONArray(new ArrayList<>(Arrays.asList("Password doesn't match"))));
                return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);
              }
                
        }
        
           
        @GetMapping(value="updateRole")       
        public ResponseEntity<String> updateRole(@AuthenticationPrincipal CustomUserDetails user,  HttpServletRequest request,                                    
                                      @RequestParam("roleid") double id                                      
                                      )
        {   
          JSONObject obj=new JSONObject();
          
          if(id>0)
          {
              if(id==AuthManager.getStaffPriority())
              {
                String url=StringUtils.substringBefore(request.getRequestURL(), request.getContextPath())+request.getContextPath();
                url+="/auth/api/admin/upgradeUserRoleAtStaff";                
                userService.sendEnableStaffRoleMail(user, url);
                obj.put("status","success");
                return new ResponseEntity<>(obj.toString(), HttpStatus.OK);
              }
              obj.put("status","failed");
              return new ResponseEntity<>(obj.toString(), HttpStatus.NOT_IMPLEMENTED);
          }
          else
          {
              obj.put("status","failed");
              return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);
          } 
        }
       

}