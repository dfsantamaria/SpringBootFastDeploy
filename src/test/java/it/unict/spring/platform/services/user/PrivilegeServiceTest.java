package it.unict.spring.platform.services.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.Application;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.service.user.OrganizationService;
import it.unict.spring.platform.service.user.PrivilegeService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


//remember to modify the file schema.sql if the name of "user" schema changes

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrivilegeServiceTest
{

    
    @SpyBean
    private  PrivilegeService privServ;    
    private String privilege="admin";
        
    
    @BeforeAll   
    public void createOrganization()
    {
        assertNotNull(privServ.save(new Privilege(privilege, "label", "type", 0.0)));
       // Organization persist = entityManager.persist(new Organization(organization));
       // assertNotNull(persist);
    }
    
    @AfterAll
    public void clear()
    {
        Optional<Privilege> orgs = privServ.findByName(privilege);
        privServ.delete(orgs.get());
    }
    
    @Test
    public void testFindByName()
    {
        
        Optional<Privilege> orgs = privServ.findByName(privilege);
        assertFalse(orgs.isEmpty());
        assertEquals(orgs.get().getName(),privilege); 
        
    }
}
