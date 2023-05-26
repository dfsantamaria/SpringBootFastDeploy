package it.unict.it.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.Application;
import it.unict.spring.platform.dto.user.AccountPasswordDTO;
import it.unict.spring.platform.dto.user.TokenPasswordDTO;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.SecureTokenService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    @Transactional
    public void isRecoverPasswordSavingData() throws Exception
    {
      UserAccount user= new UserAccount("testNameRecover", "PlainPassword", "testRecover@mail.com", UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate());
      service.save(user);
      SecureToken token = tokenService.generateToken(user, "RPass");
      tokenService.save(token);
      
      TokenPasswordDTO dto = new TokenPasswordDTO();
      dto.setPassword(new AccountPasswordDTO("the_newPassword","the_newPassword"));
      dto.setToken(token.getToken());
      
              
      mvc.perform(MockMvcRequestBuilders.post(("/public/api/access/recover/changePassword")).characterEncoding("utf-8")
                                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))              
                
           .andExpect(status().isOk()); 
      UserAccount changeUser=service.findById(user.getId());
      assertTrue(service.comparePassword(changeUser.getPassword(), "the_newPassword"));
      service.deleteUser(changeUser); 
      
    }
}
