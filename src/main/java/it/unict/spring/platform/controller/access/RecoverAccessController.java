package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.dto.user.TokenPasswordDTO;
import it.unict.spring.platform.dto.user.UserAccountDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/access/recover")
public class RecoverAccessController
{    
    @Autowired
    UserService userService;
    
    private static final Logger applogger = LoggerFactory.getLogger(RecoverAccessController.class);  
    
     
    @GetMapping(value="sendRecoverPassword", consumes = {"application/json"})
    public ResponseEntity<String> sendRecoverPassword(@RequestBody UserAccountDTO user, HttpServletRequest request) 
    {
       Optional<UserAccount> users = userService.findByMail(user.getMail());        
       if(!users.isEmpty())
       {
         userService.sendRecoverPasswordMail(users.get(), new JSONObject());
       }       
       JSONObject obj=new JSONObject();
       obj.put("status","success");
       return new ResponseEntity<>(obj.toString(),HttpStatus.OK);        
    }
    
     
    @PostMapping(value="changePassword", consumes = {"application/json"})
    public ResponseEntity<String> changePassword(@Valid @RequestBody TokenPasswordDTO tokenpassword)
    {  
       JSONObject obj=new JSONObject();     
       if(userService.verifyPasswordChangedToken(tokenpassword.getToken(), tokenpassword.getPassword().getPassword()))
       {            
        obj.put("status","success");
        return new ResponseEntity<>(obj.toString(),HttpStatus.OK);
       }  
       else
       {
         //model.addAttribute("passError", "Link not valid");
         //response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         obj.put("status","failed");
         return new ResponseEntity<>(obj.toString(),HttpStatus.BAD_REQUEST);
       }
      
    }
    
}