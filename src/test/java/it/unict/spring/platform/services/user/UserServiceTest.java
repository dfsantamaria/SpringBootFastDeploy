package it.unict.spring.platform.services.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

//remember to modify the file schema.sql if the name of "data" schema changes

import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.ANY, connection=EmbeddedDatabaseConnection.H2)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes={UserService.class}) // don't user or h2 will not create USERS catalog, loader=AnnotationConfigContextLoader.class)
@EntityScan(basePackages =  {"it.unict.spring.platform.persistence.model.*"})
//@EnableJpaRepositories(basePackages = {"it.unict.spring.application.persistence.repository"})
@ComponentScan(basePackages = {"it.unict.spring.platform.service.*", "it.unict.spring.platform.configuration.*"})

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
       List<UserAccount> users = userServ.findByUsername(username);
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
       assertTrue(users.get(0).isEnabled());
       assertEquals(users.get(0).getMail(), mail);
    }
    
    @AfterAll
    public void clear()
    {
       List<UserAccount> users = userServ.findByUsername(username);
       userServ.delete(users.get(0));
    }
    
    @Test
    public void testFindByName()
    {
        
        List<UserAccount> users = userServ.findByUsername(username);
        assertEquals(1, users.size());
        assertEquals(users.get(0).getUsername(), username); 
        
    }

}
