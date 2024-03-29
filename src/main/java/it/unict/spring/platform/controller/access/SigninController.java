package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.configuration.security.login.CustomLoginSuccessHandler;
import it.unict.spring.platform.service.platform.PlatformStatusService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/public/api/access/login")
public class SigninController
{         
     @Autowired
     PlatformStatusService platformService;
     @RequestMapping("signin")
     public ModelAndView viewlogin(HttpServletRequest request, Model model, Authentication authentication)
     {  
        if(authentication!=null) 
            return new ModelAndView("redirect:/auth/api/all/accountView");
        String referer = request.getHeader("Referer"); //Get previous URL before call '/login'
        //save referer URL to session, for later use on CustomAuthenticationSuccesshandler
        request.getSession().setAttribute(CustomLoginSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME, referer);        
        return new ModelAndView("public/access/login/signin");
     }      
}

