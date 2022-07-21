package it.unict.it.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
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
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class LoginControllerTest
{       
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void isLoginPubliclyAvailable() throws Exception 
    {
      mvc.perform(MockMvcRequestBuilders.get(("/public/api/access/login/signin"))).andExpect(status().isOk());      
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
    mvc.perform(formLogin("/public/api/access/login/signin").user("dfsantamaria").password("lll@@"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));    
  }
  
  @Test
  public void cannotLog() throws Exception
  {
    mvc.perform(formLogin("/public/api/access/login/signin").user("nonregistered").password("any"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/public/api/access/login/signin?error"));
  }
  
}
