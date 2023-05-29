package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.dto.user.LoginDTO;
import it.unict.spring.platform.service.platform.PlatformStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;


@RestController
@RequestMapping("/public/api/access/login/")
public class SigninController
{     
     @Autowired
     private AuthenticationManager authenticationManager;  
    
     @Autowired
     PlatformStatusService platformService;
     @PostMapping("signin")
     public ResponseEntity<String> login(@RequestBody LoginDTO logindto)
     { 
       JSONObject obj=new JSONObject();  
       try
       {
        authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(logindto.getUsername(), logindto.getPassword()));
        obj.put("status","success");
        return new ResponseEntity<>(obj.toString(), HttpStatus.OK);
       }
       catch(AuthenticationException e)
       {
         obj.put("status","success");
         return new ResponseEntity<>(obj.toString(), HttpStatus.FORBIDDEN); 
       }         
     }      
}

