package it.unict.spring.platform.controller.user.admin;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.dto.user.PageDTO;
import it.unict.spring.platform.dto.user.UserSearchDTO;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.UserAccount;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth/api/admin")
public class AdminController
{
   @Autowired
   UserService userService; 
   
   @RequestMapping(value = "/usersView", method = RequestMethod.GET)
   public ModelAndView usersView(Locale locale,
                                 @ModelAttribute("usersearchdto") UserSearchDTO usersearchdto,
                                 BindingResult searchBindResult,
                                 @ModelAttribute("pageSearch") PageDTO pageSearch,
                                 BindingResult pageSearchBind,
                                 @AuthenticationPrincipal CustomUserDetails user, Model model)
   {   
     Set<Privilege> setPriv = userService.findPrivilegeFromCustomUserDetails(user);
     ModelTemplate.setNavBar(setPriv.iterator(), model);
     PageDTO page=new PageDTO();       
     page.setItemsSet(new ArrayList<>(Arrays.asList(5,50,100)));
     model.addAttribute("paging", page);
     return new ModelAndView("auth/admin/home/users");
   } 

   @RequestMapping(value = "searchuser", method = RequestMethod.POST)
   public ModelAndView searchUser( HttpServletRequest request,
                                   HttpServletResponse response,
                                   @ModelAttribute("usersearchdto") UserSearchDTO usersearchdto,
                                   BindingResult searchBindResult,
                                   @ModelAttribute("paging") PageDTO pageSearch,
                                   BindingResult pageSearchBind,
                                   @AuthenticationPrincipal CustomUserDetails user, 
                                   Model model, RedirectAttributes attributes) 
   {         
      Page<UserAccount> pages = userService.searchUserFromUserDTO(usersearchdto, PageRequest.of(0, 10)); 
      if(pages != null)
      {
        List<UserSearchDTO> results = userService.createUserSearchDTOFromPage(pages);
        if(!results.isEmpty())
          attributes.addFlashAttribute("result", results); 
         else 
          attributes.addFlashAttribute("result", null); 
      }
      attributes.addFlashAttribute("usersearchdto", model.getAttribute("usersearchdto"));
      return new ModelAndView("redirect:/auth/api/admin/usersView");
   }  
   

}

                                      