package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/public/api/access/login")
public class SigninController
{         
     @RequestMapping("signin")
     public ModelAndView viewlogin(HttpServletRequest request, Model model)
     {              
        return new ModelAndView("public/access/login/signin");
     }      
}

