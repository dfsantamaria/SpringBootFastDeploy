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
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
      List<UserAccount> list = service.findByMail(user.getMail());
      if(!list.isEmpty())
          service.delete(list.get(0));   
      
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/register/registerUser"))
                                               .param("username", user.getUsername())
                                               .param("password", user.getPassword())
                                               .param("confirmPassword", user.getPassword())
                                               .param("mail", user.getMail())
                                               .param("name", "test organization").with(csrf())
                )
           .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
     list = service.findByMail(user.getMail());
     if(!list.isEmpty())
          service.delete(list.get(0));   
    }
}