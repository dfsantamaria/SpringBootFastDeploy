package it.unict.spring.platform.services.user;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import it.unict.spring.platform.Application;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.service.security.CustomUserDetailsService;
import it.unict.spring.platform.service.user.OrganizationService;
import it.unict.spring.platform.service.user.PrivilegeService;
import it.unict.spring.platform.service.user.SecureTokenService;
import it.unict.spring.platform.service.user.UserLoginService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserLoginServiceTest
{
   @SpyBean
   CustomUserDetailsService  detailService;
   
     @Autowired
  UserService userService;
  @Autowired
  OrganizationService orgService;
  @Autowired
  PrivilegeService privService;
  @Autowired
  SecureTokenService tokenService;
  @Autowired
  UserLoginService loginService;
  
  private final String username="username";
  private final String mail="mail";
  private final String org = "org";
  
  private UserAccount theuser;  
  private SecureToken thesecuretoken;
  
  @BeforeAll
  public void pushData() throws MultipleUsersFoundException
  {
          theuser = userService.getSuperAdminUser(username, 
                                                      "lll@@",
                                                       mail,
                                                       UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                         org);
          userService.setEnabled(theuser, true); 
          userService.save(theuser);
          thesecuretoken = tokenService.generateToken(theuser, "FReg");
          thesecuretoken.setIsConsumed(Timestamp.valueOf(LocalDateTime.now()));
          tokenService.save(thesecuretoken);   
          userService.createLoginInfo(theuser);
  }
  
  @Test
  public void testUpdate()
  {
       UserAccount user = userService.findByUsername(username).get();
       UserLogin login = loginService.findById(user.getId()).get();
       loginService.updateLoginFailDate(login);
       loginService.updateLoginFailDate(login);
       loginService.updateLoginFailDate(login);
       assertEquals(login.getFailCount(), 3);
       assertNotNull(login.getLastFailDate());
       loginService.resetLoginFail(login);
       assertEquals(login.getFailCount(),0);
       assertNull(login.getLastFailDate());
  }
  
  @AfterAll
  public void clear()
  {
    userService.deleteUser(theuser);
  }
}