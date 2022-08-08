package it.unict.spring.platform.services.data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


//remember to modify the file schema.sql if the name of "data" schema changes

import it.unict.spring.platform.Application;
import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.data.Data;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.data.DataService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataServiceTest
{

    @SpyBean
    private  DataService dataServ;        
    private String todo="something";
    @SpyBean
    private  UserService userServ;    
    private final String mail="testData@unict.it";
    private final String username = "testDataatunict";
        
    
    @BeforeAll   
    public void createData() throws MultipleUsersFoundException
    {
       
       Optional<UserAccount> users = userServ.findByUsername(username);
       UserAccount user;
       if(users.isEmpty())
       {
        user = userServ.getSuperAdminUser(username, "lll@@", mail,
                                                         UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                         "My nice organization");
        userServ.save(user);
       }
       else 
           user=users.get();
       
       Optional<Data> data = dataServ.findByName(todo);
       if(data.isEmpty())
       {
        Data d=new Data(todo);
        d.setUser(user);
        dataServ.save(d);
       }
       // Organization persist = entityManager.persist(new Organization(organization));
       // assertNotNull(persist);
    }
    
    
    @AfterAll
    public void clear()
    {
       Optional<Data> data = dataServ.findByName(todo);
       dataServ.delete(data.get());
       Optional<UserAccount> users = userServ.findByUsername(username);
       userServ.delete(users.get());       
    }
    
    @Test
    public void testFindByName()
    {        
        Optional<Data> orgs = dataServ.findByName(todo);
        assertFalse(orgs.isEmpty());
        assertEquals(orgs.get().getName(),todo);         
    }
}
