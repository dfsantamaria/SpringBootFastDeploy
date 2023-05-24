package it.unict.it.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.Application;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
@Disabled
public class LoginControllerTest
{       
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserLoginService loginService;
    
    private final String username = "dfsantamaria";
    private final String password = "lll@@";
    
    @Test
    public void isLoginPubliclyAvailable() throws Exception 
    {
      mvc.perform(MockMvcRequestBuilders.get(("/public/api/access/login/signin"))).andExpect(status().isMethodNotAllowed());      
    }
    
    @Test
    @WithMockUser(username = "admin", roles = { "STAFF" })
    public void isLogoutForLoggedUser() throws Exception
    {
     mvc.perform(MockMvcRequestBuilders.get(("/auth/api/access/login/signout"))). 
              andExpect(status().is3xxRedirection())
             .andExpect(redirectedUrl("/public/api/access/login/signin?logout"));
              //.andExpect(redirectedUrl("/public/api/access/login/signin?logout"));
    }
    
    
  @Test
  @Transactional
  public void canLog() throws Exception
  {
    mvc.perform(formLogin("/public/api/access/login/signin").user(username).password(password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/api/all/accountView"));    
    
    UserAccount account=userService.findByUsername(username).get();
    Long id = account.getId();
    UserLogin login = loginService.findById(id).get();
    assertEquals(login.getFailCount(), 0);
    
    login= account.getLogin();
    assertEquals(login.getFailCount(), 0);
    
    
    
  }
  
  @Test
  public void cannotLog() throws Exception
  {
    mvc.perform(formLogin("/public/api/access/login/signin").user("nonregistered").password("any"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/public/api/access/login/signin?errorLogin"));    
  }
  
}
