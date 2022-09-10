package it.unict.spring.platform.controller.user.authall;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.dto.user.AccountPasswordDTO;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.service.user.UserRegisterService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import it.unict.spring.platform.utility.user.ModelTemplate;

@Controller
@RequestMapping("/auth/api/all")
/*
* Manage the landing page of logged users
*/
public class AuthLanding
{
    @Autowired
    UserService userService;
    @Autowired
    UserRegisterService regService;
    
	@RequestMapping(value = "/accountView", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, @AuthenticationPrincipal CustomUserDetails user, Model model)
        {
	 this.setModel(model, user);
	 return new ModelAndView("auth/all/home/accountview");
	}
        
        @RequestMapping(value = "/updateData", method = RequestMethod.POST)
        public ModelAndView updateData(
                                      @AuthenticationPrincipal CustomUserDetails user,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @ModelAttribute("passdto") @Valid AccountPasswordDTO password, 
                                      BindingResult passdtoBinding,
                                      Model model)
        {
          this.setModel(model, user);
          
          if(!passdtoBinding.hasErrors()) 
           {                 
              UserAccount account=userService.findByMail(user.getMail()).get();
              if(userService.comparePassword(account.getPassword(), password.getOldpassword()))
              {
                account.setPassword(userService.encodePassword(password.getPassword()));
                userService.save(account);     
                model.addAttribute("passwordAccepted", "Password modified");
              }
              else
              {
                model.addAttribute("passwordError", "Your old password does not match");
              }
              return new ModelAndView("auth/all/home/accountview");
            }
            else
            {
              model.addAttribute("passwordError", "Check yuor password fields");
              return new ModelAndView("auth/all/home/accountview");
            }
          
        }
        
        
        /*
        * Set the model page 
        */
        private void setModel(Model model, CustomUserDetails user)
        {                    
          UserRegister reg = userService.findRegisterFromCustomUserDetail(user);          
          Organization org = userService.findOrganizationFromCustomUserDetails(user);
          Set<Privilege> setPriv = userService.findPrivilegeFromCustomUserDetails(user);
             
          //set the navigation bar depending on the user roles
          ModelTemplate.setNavBar(setPriv.iterator(), model);
          
          Iterator privileges = setPriv.iterator();
          String privs ="";
          
          while(privileges.hasNext())
          {  
           Privilege auth=((Privilege) privileges.next());   
           privs=privs.concat(auth.getDescription());
           if(privileges.hasNext())
               privs+=", ";           
          }
          model.addAttribute("viewPrivilege", privs);
          
          model.addAttribute("viewName", reg.getFirstname());
          if(!reg.getMiddlename().isBlank())
                 model.addAttribute("viewMiddleName", reg.getMiddlename());
          model.addAttribute("viewLastName", reg.getLastname());
          model.addAttribute("viewUsername", user.getUsername() );  
          model.addAttribute("viewMail",  user.getMail());
          model.addAttribute("viewOrgName", org.getName());
        }
}