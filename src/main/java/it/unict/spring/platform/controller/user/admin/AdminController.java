package it.unict.spring.platform.controller.user.admin;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import it.unict.spring.platform.utility.user.ModelTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import it.unict.spring.platform.dto.user.SearchUserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/auth/api/admin")
public class AdminController
{
   @Autowired
   UserService userService; 
   
   @RequestMapping(value = "/usersView", method = RequestMethod.GET)
   public ModelAndView usersView(Locale locale, 
                                   @ModelAttribute("searchInput") SearchUserDTO searchdto, 
                                   BindingResult searchBindResult,
                                   @AuthenticationPrincipal CustomUserDetails user, Model model)
   {   
     Set<Privilege> setPriv = userService.findPrivilegeFromCustomUserDetails(user);
     ModelTemplate.setNavBar(setPriv.iterator(), model);  
     populateSearchOptions(model, "searchoptions");
     
     return new ModelAndView("auth/admin/home/users");
   } 

   @RequestMapping(value = "searchuser", method = RequestMethod.POST)
   public ModelAndView searchUser( HttpServletRequest request,
                                   HttpServletResponse response,
                                   @ModelAttribute("searchInput") SearchUserDTO searchdto, 
                                   BindingResult searchBindResult, @AuthenticationPrincipal CustomUserDetails user, 
                                   Model model) 
   {
      populateSearchOptions(model, "searchoptions"); 
      System.out.println(searchdto.getKey()+" "+searchdto.getParameters().toString());     
      return new ModelAndView("auth/admin/home/users");
   }

    private void populateSearchOptions(Model model, String tag)
    {        
     List<Pair<String,String>> options = new ArrayList<>();
     options.add(Pair.of("firstname","First Name"));
     options.add(Pair.of("middlename", "Middle Name"));
     options.add(Pair.of("lastname","Last Name"));
     options.add(Pair.of("username","Username"));
     options.add(Pair.of("mail","E-Mail"));
     options.add(Pair.of("organization","Organization"));     
     model.addAttribute(tag, options);
    }
}

                                      