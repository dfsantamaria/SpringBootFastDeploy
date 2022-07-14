package it.unict.it.spring.application.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
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
      mvc.perform(MockMvcRequestBuilders.get(("/public/access/signin"))).andExpect(status().isOk());
      mvc.perform(MockMvcRequestBuilders.get(("/public/access/signout"))). 
              andExpect(status().is3xxRedirection()).      
              andExpect(redirectedUrl("/public/access/signin?logout"));
    }
    
  @Test
  public void canLog() throws Exception
  {
    mvc.perform(formLogin("/public/access/signin").user("dfsantamaria").password("lll@@"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    
            
    mvc
             .perform(logout("/public/access/signout"))
             .andExpect(status().is3xxRedirection())
             .andExpect(redirectedUrl("/public/access/signin?logout"));
  }
  
  @Test
  public void cannotLog() throws Exception
  {
    mvc.perform(formLogin("/public/access/signin").user("nonregistered").password("any"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/public/access/signin?error"));
  }
  
}
