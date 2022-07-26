package it.unict.spring.platform.controller.webmcv;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */




import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.ENGLISH); 
		String formattedDate = dateFormat.format(date); 
		model.addAttribute("serverTime", formattedDate ); 
		return new ModelAndView("auth/all/home/accountview");
	}
        
        @RequestMapping(value = "/updateData", method = RequestMethod.POST)
        public ModelAndView updateData(
                                      @AuthenticationPrincipal CustomUserDetails user,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam("password") String password,
                                      @RequestParam("confirmPassword") String confirmpassword,
                                      Model model)
        {
          //System.out.println(user.getMail());
          if(password!=null && !password.isEmpty())
          {
            if(password.equals(confirmpassword))
            {
              UserAccount account=userService.findByMail(user.getMail()).get(0);
              account.setPassword(userService.encodePassword(password));
              userService.save(account);              
              return new ModelAndView("redirect:/auth/api/all/accountView?accepted");
            }
            else
                return new ModelAndView("redirect:/auth/api/all/accountView?passwordError");
          }
          return new ModelAndView("redirect:/auth/api/all/accountView");
        }
}