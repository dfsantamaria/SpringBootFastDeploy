package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.configuration.security.login.CustomLoginSuccessHandler;
import it.unict.spring.platform.dto.user.LoginDTO;
import it.unict.spring.platform.service.platform.PlatformStatusService;
import it.unict.spring.platform.service.security.CustomUserDetailsService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/public/api/access/login")
public class SigninController
{     
     @Autowired
     private AuthenticationManager authenticationManager;  
    
     @Autowired
     PlatformStatusService platformService;
     @PutMapping("signin")
     public ResponseEntity<String> login(@RequestBody LoginDTO logindto)
     { 
       try
       {
        authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(logindto.getUsername(), logindto.getPassword()));
       return new ResponseEntity<>("logged", HttpStatus.OK);
       }
       catch(AuthenticationException e)
       {
         return new ResponseEntity<>("failed", HttpStatus.FORBIDDEN); 
       }         
     }      
}

