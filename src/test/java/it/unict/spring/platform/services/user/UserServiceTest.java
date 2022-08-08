package it.unict.spring.platform.services.user;

import it.unict.spring.platform.Application;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

//remember to modify the file schema.sql if the name of "data" schema changes

import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static junit.framework.TestCase.assertFalse;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest
{

    //@Autowired
   // private TestEntityManager entityManager;
    @SpyBean
    private  UserService userServ;    
    private final String mail="test@unict.it";
    private final String username = "testatunict";
        
   
    @BeforeAll    
    public void createUser() throws MultipleUsersFoundException
    {
       Optional<UserAccount> users = userServ.findByUsername(username);
       if(users.isEmpty())
       {
          UserAccount user = userServ.getSuperAdminUser(username, "lll@@", mail,
                                                         UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                         "My nice organization");
          userServ.setEnabled(user, true);  
          userServ.save(user);
       }
       users = userServ.findByUsername(username);
       assertTrue(users.get().isEnabled());
       assertEquals(users.get().getMail(), mail);
    }
    
    @AfterAll
    public void clear()
    {
       Optional<UserAccount> users = userServ.findByUsername(username);
       userServ.delete(users.get());
    }
    
    @Test
    public void testFindByName()
    {
        
        Optional<UserAccount> users = userServ.findByUsername(username);
        assertFalse(users.isEmpty());
        assertEquals(users.get().getUsername(), username); 
        
    }

}
