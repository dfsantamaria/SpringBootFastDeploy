package it.unict.it.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.Application;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.time.LocalDateTime;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class RegistrationTest
{       
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService service;
    
    @Test
    public void isRegistrationPubliclyAvailable() throws Exception 
    {
      mvc.perform(MockMvcRequestBuilders.get(("/public/api/access/registration/register"))).andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    public void isRegistrationSavingData() throws Exception
    {
      UserAccount user= new UserAccount("testName", "PlainPassword", "test@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate());
      this.clearUser(user);       
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/registration/registerUser"))
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
    public void isRegistrationFailingPasswordMismatch() throws Exception
    {
      UserAccount user= new UserAccount("testName", "PlainPassword", "test@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate());
      this.clearUser(user);      
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/registration/registerUser"))
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