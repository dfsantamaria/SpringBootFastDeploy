package it.unict.spring.application.persistence;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.persistence.model.user.Privilege;
import it.unict.spring.application.service.user.OrganizationService;
import it.unict.spring.application.service.user.PrivilegeService;
import java.util.List;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

//remember to modify the file schema.sql if the name of "user" schema changes

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.ANY, connection=EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes={OrganizationService.class}, loader = AnnotationConfigContextLoader.class)
@EntityScan(basePackages =  {"it.unict.spring.application.persistence.model"})
@EnableJpaRepositories(basePackages = {"it.unict.spring.application.persistence.repository"})
@Transactional
public class PrivilegeServiceTest
{

    
    @SpyBean
    private  PrivilegeService privServ;    
    private String privilege="admin";
        
    
    @BeforeEach    
    public void createOrganization()
    {
        assertNotNull(privServ.save(new Privilege(privilege)));
       // Organization persist = entityManager.persist(new Organization(organization));
       // assertNotNull(persist);
    }
    
    @Test
    public void testFindByName()
    {
        
        List<Privilege> orgs = privServ.findByName(privilege);
        assertEquals(1, orgs.size());
        assertEquals(orgs.get(0).getName(),privilege); 
        
    }
}
