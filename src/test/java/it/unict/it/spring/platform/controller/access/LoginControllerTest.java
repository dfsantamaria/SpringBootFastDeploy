package it.unict.it.spring.platform.controller.access;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.spring.platform.Application;
import it.unict.spring.platform.dto.user.LoginDTO;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.MediaType;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class LoginControllerTest
{   
    @Autowired
    ObjectMapper objectMapper;
    
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
              andExpect(status().isOk());
    }
    
    
  @Test
  @Transactional
  public void canLog() throws Exception
  {
    LoginDTO dto=new LoginDTO(password, username);    
    
    mvc.perform(MockMvcRequestBuilders.put(("/public/api/access/login/signin")).characterEncoding("utf-8") 
                                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))              
                                                .andExpect(status().isOk());
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
    LoginDTO dto=new LoginDTO("apassword", "ausername");  
    mvc.perform(MockMvcRequestBuilders.put(("/public/api/access/login/signin")).characterEncoding("utf-8") 
                                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))              
                                                .andExpect(status().isForbidden());
  }
  
}
