package it.unict.it.spring.platform.controller.access;


/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.Application;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.SecureTokenService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.List;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc

public class RecoverAccessControllerTest 
{
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService service;
    @Autowired
    private SecureTokenService tokenService;
    
    @Test
    @Transactional
    public void isRecoverPasswordSavingData() throws Exception
    {
      UserAccount user= new UserAccount("testNameRecover", "PlainPassword", "testRecover@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate());
      service.save(user);
      SecureToken token = tokenService.generateToken(user, "RPass");
      tokenService.save(token);
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/recover/changePassword"))
                                               .param("token", token.getToken())
                                               .param("password", "the_newPassword")
                                               .param("confirmPassword", "the_newPassword"))
                
           .andExpect(status().isOk()); 
      UserAccount changeUser=service.findById(user.getId());
      assertTrue(service.comparePassword(changeUser.getPassword(), "the_newPassword"));
      service.deleteUser(changeUser); 
      
    }
}
