package it.unict.spring.platform.configuration.security.login;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
    //@Autowired
    //UserService userService;
    //@Autowired
    //UserLoginService loginService;
        
    @Override       
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException 
    {         
        
        //CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        //UserLogin logInfo=userService.findByMail(details.getMail()).get().getLogin();
        //loginService.resetLoginFail(logInfo);
        super.onAuthenticationSuccess(request, response, authentication);
    } 
}