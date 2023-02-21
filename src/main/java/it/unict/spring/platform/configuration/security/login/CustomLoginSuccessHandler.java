package it.unict.spring.platform.configuration.security.login;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
         
    @Autowired
    UserLoginService loginService;
    @Autowired
    UserService userService;
    public static final String REDIRECT_URL_SESSION_ATTRIBUTE_NAME = "REDIRECT_URL";
    @Autowired 
    private HttpServletRequest request;
    
    @Override       
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException 
    {       
       CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
       UserAccount user=userService.findByMail(details.getMail()).get();
       UserLogin logInfo= user.getLogin();
       loginService.resetLoginFail(logInfo);
       if(user.isAccountSuspended())
         userService.setSuspended(user, false);       
       // getRedirectStrategy().sendRedirect(request, response, "/auth/api/all/accountView");     
       Object redirectURLObject = request.getSession().getAttribute(REDIRECT_URL_SESSION_ATTRIBUTE_NAME);
       String url=StringUtils.substringBefore(request.getRequestURL().toString(), request.getContextPath())       
                                  +request.getContextPath(); 
       
       if(redirectURLObject != null && redirectURLObject.toString().startsWith(url))
         setDefaultTargetUrl(redirectURLObject.toString());
       else
         setDefaultTargetUrl("/auth/api/all/accountView");        

       request.getSession().removeAttribute(REDIRECT_URL_SESSION_ATTRIBUTE_NAME);
       loginService.updateLoginSuccessDate(logInfo);
       super.onAuthenticationSuccess(request, response, authentication);       
    } 
}