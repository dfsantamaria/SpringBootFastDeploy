package it.unict.spring.application.persistence;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

//remember to modify the file schema.sql if the name of "data" schema changes

import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.persistence.model.data.Data;
import it.unict.spring.application.persistence.model.user.Users;
import it.unict.spring.application.service.data.DataService;
import it.unict.spring.application.service.user.OrganizationService;
import it.unict.spring.application.service.user.UserService;
import java.util.List;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.ANY, connection=EmbeddedDatabaseConnection.H2)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes={UserService.class}) // don't user or h2 will not create USERS catalog, loader=AnnotationConfigContextLoader.class)
@EntityScan(basePackages =  {"it.unict.spring.application.persistence.model.*"})
//@EnableJpaRepositories(basePackages = {"it.unict.spring.application.persistence.repository"})
@ComponentScan(basePackages = {"it.unict.spring.application.service.*", "it.unict.spring.application.configuration.*"})

@Transactional
public class UserServiceTest
{

    //@Autowired
   // private TestEntityManager entityManager;
    @SpyBean
    private  UserService userServ;    
    private final String mail="daniele.santamaria@unict.it";
    private final String username = "dfsantamaria";
        
    
    @BeforeEach    
    public void createUser() throws MultipleUsersFoundException
    {
       List<Users> users = userServ.findByUsername(username);
       if(users.isEmpty())
         userServ.getOrSetSuperAdminUser(username, "lll@@", mail, "Univeristy of Catania");
       users = userServ.findByUsername(username);
       assertEquals(users.get(0).getMail(), mail);
    }
    
    @Test
    public void testFindByName()
    {
        
        List<Users> users = userServ.findByUsername(username);
        assertEquals(1, users.size());
        assertEquals(users.get(0).getUsername(), username); 
        
    }
}
