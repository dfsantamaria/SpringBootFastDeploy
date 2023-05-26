package it.unict.it.spring.platform.controller.access;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.Application;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.SecureTokenService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class RegistrationControllerTest
{       
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService service;
    @Autowired
    private SecureTokenService tokenService;
    
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
      service.deleteUser(user);       
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/registration/registerUser"))
                                               .param("username", user.getUsername())
                                               .param("password.password", user.getPassword())
                                               .param("password.confirmPassword", user.getPassword())
                                               .param("mail", user.getMail())
                                               .param("name", "test organization")
                                               .param("role","1").with(csrf())
                )
           .andExpect(status().isOk());                
      service.deleteUser(user); 
    }
    
    @Test
    @Transactional
    public void isRegistrationFailingPasswordMismatch() throws Exception
    {
      UserAccount user= new UserAccount("testName", "PlainPassword", "test@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate());
      service.deleteUser(user);      
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/registration/registerUser"))
                                               .param("username", user.getUsername())
                                               .param("password", user.getPassword())
                                               .param("confirmPassword", "mismatchPassword")
                                               .param("mail", user.getMail())
                                               .param("name", "test organization").with(csrf())
                )
           .andExpect(status().isBadRequest());
         //  .andExpect(model().attribute("errorMessage", "Errors occured, check your fields"));
      
      service.deleteUser(user);   
    }
    
    
    @Test
    @Transactional
    public void isVerifyingToken() throws Exception
    {
      UserAccount user= new UserAccount("testName2", "PlainPassword", "test2@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate());
      service.deleteUser(user);
      
      user = service.getStandardUser("testName2", "PlainPassword", "test2@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(), "org");
      service.save(user);
      SecureToken token=service.assignTokenToUser(user, "FReg");
      tokenService.save(token);
      service.save(user);
      
      assertFalse(user.isEnabled());
      
      mvc.perform(MockMvcRequestBuilders.get("/public/api/access/registration/registerUser/registrationConfirm").param("token", token.getToken()))
               .andExpect(status().isOk());                

      Optional<UserAccount> usersByName = service.findByUsername(user.getUsername());
      assertFalse(usersByName.isEmpty());
      UserAccount userByName = usersByName.get();
      assertTrue(userByName.isEnabled());
      UserAccount userById = service.findById(user.getId());
      assertTrue(userById.isEnabled());
      
      service.deleteUser(user); 
    }
    
       
    
    
    @Test
    @Transactional
    public void isResendRegistrationSavingData() throws Exception
    {
     if(service.findByMail("test3@mail.com").isEmpty())   
     {
       UserAccount user =  service.getStandardUser("testName3", "PlainPassword", "test3@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(), "organization");
       service.save(user);
     }
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/registration/confirmResendRegister")).with(csrf())
                                               .param("username", "testName3")
                                               .param("password", "PlainPassword")
                                              
                )
           .andExpect(status().isOk());                
      service.deleteUser( (service.findByMail("test3@mail.com")).get()); 
    }   
}