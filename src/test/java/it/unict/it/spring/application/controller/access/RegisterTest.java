package it.unict.it.spring.application.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.Application;
import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.service.user.UserService;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class RegisterTest
{       
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService service;
    
    @Test
    public void isRegisterPubliclyAvailable() throws Exception 
    {
      mvc.perform(MockMvcRequestBuilders.get(("/public/api/access/register/register"))).andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    public void isRegisterSavingData() throws Exception
    {
      UserAccount user= new UserAccount("testName", "PlainPassword", "test@mail.com");
      this.clearUser(user);       
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/register/registerUser"))
                                               .param("username", user.getUsername())
                                               .param("password", user.getPassword())
                                               .param("confirmPassword", user.getPassword())
                                               .param("mail", user.getMail())
                                               .param("name", "test organization").with(csrf())
                )
           .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));                
    this.clearUser(user); 
    }
    
    @Test
    @Transactional
    public void isRegisterFailingPasswordMismatch() throws Exception
    {
      UserAccount user= new UserAccount("testName", "PlainPassword", "test@mail.com");
      this.clearUser(user);      
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/register/registerUser"))
                                               .param("username", user.getUsername())
                                               .param("password", user.getPassword())
                                               .param("confirmPassword", "mismatchPassword")
                                               .param("mail", user.getMail())
                                               .param("name", "test organization").with(csrf())
                )
           .andExpect(status().isBadRequest());
         //  .andExpect(model().attribute("errorMessage", "Errors occured, check your fields"));
      
      this.clearUser(user);   
    }
    
    private void clearUser(UserAccount user)
    {
      List<UserAccount> list = service.findByMail(user.getMail());
      if(!list.isEmpty())
          service.delete(list.get(0));   
    }
}