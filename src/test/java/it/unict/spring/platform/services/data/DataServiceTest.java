package it.unict.spring.platform.services.data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

//remember to modify the file schema.sql if the name of "data" schema changes

import it.unict.spring.platform.persistence.model.data.Data;
import it.unict.spring.platform.service.data.DataService;
import it.unict.spring.platform.service.user.OrganizationService;
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

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.ANY, connection=EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes={OrganizationService.class}, loader = AnnotationConfigContextLoader.class)
@EntityScan(basePackages =  {"it.unict.spring.platform.persistence.model"})
@EnableJpaRepositories(basePackages = {"it.unict.spring.platform.persistence.repository"})
@Transactional
public class DataServiceTest
{

    @SpyBean
    private  DataService dataServ;    
    private String todo="something";
        
    
    @BeforeEach    
    public void createOrganization()
    {
        assertNotNull(dataServ.save(new Data(todo)));
       // Organization persist = entityManager.persist(new Organization(organization));
       // assertNotNull(persist);
    }
    
    @Test
    public void testFindByName()
    {        
        List<Data> orgs = dataServ.findByName(todo);
        assertEquals(1, orgs.size());
        assertEquals(orgs.get(0).getName(),todo);         
    }
}
