package it.unict.spring.platform.controller.user.superadmin;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import it.unict.spring.platform.utility.user.ModelTemplate;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/auth/api/superadmin/platform")
public class PlatformController
{
    @Autowired
    UserService userService;
    
    @RequestMapping(value = "/viewPlatformManagement", method = RequestMethod.GET)
    public ModelAndView platformManagView(Locale locale, @AuthenticationPrincipal CustomUserDetails user, Model model)
    {
      Set<Privilege> setPriv = userService.findPrivilegeFromCustomUserDetails(user);  
      ModelTemplate.setNavBar(setPriv.iterator(), model); 
      return new ModelAndView("auth/superadmin/platform/viewPlatformManagement"); 
    }
}
