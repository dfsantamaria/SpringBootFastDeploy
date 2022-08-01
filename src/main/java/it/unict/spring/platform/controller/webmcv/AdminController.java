package it.unict.spring.platform.controller.webmcv;

import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.util.Locale;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/auth/api/admin")
public class AdminController
{
   @RequestMapping(value = "/usersView", method = RequestMethod.GET)
   public ModelAndView usersView(Locale locale, @AuthenticationPrincipal CustomUserDetails user, Model model)
   {     
     return new ModelAndView("auth/admin/home/users");
   }    
}
