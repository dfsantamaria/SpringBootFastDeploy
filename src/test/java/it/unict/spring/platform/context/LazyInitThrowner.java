package it.unict.spring.platform.context;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */



import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.service.user.PrivilegeService;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.OrganizationService;
import it.unict.spring.platform.service.user.SecureTokenService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LazyInitThrowner
{
  @Autowired
  UserService userService;
  @Autowired
  OrganizationService orgService;
  @Autowired
  PrivilegeService privService;
  @Autowired
  SecureTokenService tokenService;
  
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
  }
  
  @AfterAll
  public void clear()
  {
    userService.deleteUser(theuser);
  }
  
  
  @Test
  @Transactional
  public void throwsTokenLazyInitializationError()
  {
      UserAccount user = userService.findByMail(mail).get(0);
      Set<SecureToken> tokens = user.getTokens();
      assertEquals(tokens.iterator().next().getTokenType(), "FReg");      
  }
  
  
  @Test
  @Transactional
  public void throwsPrivilegeLazyInitializationError()
  {
      UserAccount user = userService.findByMail(mail).get(0);
      Set<Privilege> privileges = user.getPrivileges();
      assertEquals(privileges.iterator().next().getName(), "ROLE_SUPERADMIN");
     
  }
  
  
  @Test
  @Transactional
  public void throwsOrganizationLazyInitializationError()
  {
      UserAccount user = userService.findByMail(mail).get(0);
      Set<Organization> organization = user.getOrganization();
      assertEquals(organization.iterator().next().getName(), org);
  }
  
  
  @Test
  @Transactional
  public void throwsUserFromOrganizationLazyInitializationError()
  {
      List<Organization> orgs = orgService.findByName(org);
      Set<UserAccount> users = orgs.get(0).getUsers();
      assertEquals(users.iterator().next().getUsername(), username);
      
  }
  
  
  @Test
  @Transactional
  public void throwsUserFromPrivilegeLazyInitializationError()
  {
      List<Privilege> privs = privService.findByName("ROLE_SUPERADMIN");
      Set<UserAccount> users = privs.get(0).getUsers();
      Iterator<UserAccount> iterator = users.iterator();
      while(iterator.hasNext())
      {
        if(iterator.next().getMail().equals(mail))
        {
            assertTrue(true);
            return;
        }
      }
     assertTrue(false);                  
  }
  
  
  @Test
  @Transactional
  public void throwsUserFromTokenLazyInitializationError()
  {
      UserAccount user = userService.findByMail(mail).get(0);
      List<SecureToken> tokens = tokenService.findByUser(user);
      UserAccount found = tokens.get(0).getId().getUser();
      assertEquals(found.getMail(), mail);
  }
  
}
