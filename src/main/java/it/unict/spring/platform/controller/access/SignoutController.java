package it.unict.spring.platform.controller.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */



@RestController
@RequestMapping("/auth/api/access/login/")
public class SignoutController implements LogoutHandler
{        
     @GetMapping("signout")
     public ResponseEntity<String> viewlogout(HttpServletRequest request)
     {    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        this.logout(request, null, authentication); 
        JSONObject obj=new JSONObject();
        obj.put("status","success");
        return new ResponseEntity<>(obj.toString(), HttpStatus.OK);
     }  
 
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {        
        if (authentication != null)
        {    
           new SecurityContextLogoutHandler().logout(request, response, authentication);
        }     
    }
}
