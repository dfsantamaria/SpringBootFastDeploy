package it.unict.spring.platform.controller.webmcv;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */




import it.unict.spring.platform.dto.user.AccountPasswordDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth/api/all")
public class AuthLanding
{
    @Autowired
    UserService userService;
    
	@RequestMapping(value = "/accountView", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model)
        {
	 this.setModel(model);
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
          this.setModel(model);
          
          if(!passdtoBinding.hasErrors()) 
           {            
              UserAccount account=userService.findByMail(user.getMail()).get(0);
              account.setPassword(userService.encodePassword(password.getPassword()));
              userService.save(account);     
              model.addAttribute("passwordAccepted", "Password modified");
              return new ModelAndView("auth/all/home/accountview");
            }
            else
            {
              model.addAttribute("passwordError", "Check yuor password fields");
              return new ModelAndView("auth/all/home/accountview");
            }
          
        }
        
        
        private void setModel(Model model)
        {
          Date date = new Date();
	  DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.ENGLISH); 
	  String formattedDate = dateFormat.format(date); 
	  model.addAttribute("serverTime", formattedDate );           
        }
}