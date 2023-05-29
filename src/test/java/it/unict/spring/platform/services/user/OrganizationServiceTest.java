package it.unict.spring.platform.services.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.Application;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.service.user.OrganizationService;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;


//remember to modify the file schema.sql if the name of "user" schema changes

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrganizationServiceTest
{

    
    @SpyBean
    private  OrganizationService orgServ;    
    private final String organization="test_org";
        
    
    @BeforeAll    
    public void createOrganization()
    {
        assertNotNull(orgServ.save(new Organization(organization)));
       // Organization persist = entityManager.persist(new Organization(organization));
       // assertNotNull(persist);
    }
    
    @AfterAll
    public void clear()
    {
        List<Organization> orgs = orgServ.findByName(organization);
        orgServ.delete(orgs.get(0));
    }
    
    
    @Test
    public void testFindByName()
    {
        
        List<Organization> orgs = orgServ.findByName(organization);
        assertEquals(1, orgs.size());
        assertEquals(orgs.get(0).getName(),organization); 
        
    }
}
