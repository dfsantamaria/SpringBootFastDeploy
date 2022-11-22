package it.unict.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.service.platform.PlatformStatusService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/public/api/access/error")
public class DeniedController
{
  @Autowired
  PlatformStatusService platformService;
    
  @RequestMapping(value = "/forbidden", method = RequestMethod.GET)
  public ModelAndView forbiddenManager(HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication)
   {
    model.addAttribute("maintenance", platformService.isMaintenanceMode()); 
    if(authentication==null)
    {
      model.addAttribute("logged", false);
      if(!platformService.isMaintenanceMode())
          return new ModelAndView("redirect:/public/api/access/login/signin");  
    }
    else
    {
        model.addAttribute("logged", true);        
    }       
    return new ModelAndView("public/status/denied");
   }
}