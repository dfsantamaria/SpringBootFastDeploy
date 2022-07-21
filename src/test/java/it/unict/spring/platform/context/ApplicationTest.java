package it.unict.spring.platform.context;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */


import java.sql.SQLException;
import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTest
{ 
  @Autowired  
  @Qualifier("dataEntityManager")  
  public LocalContainerEntityManagerFactoryBean dataEntityManagerFactory;
    
  @Autowired
  @Qualifier("userEntityManager")
  public LocalContainerEntityManagerFactoryBean userEntityManagerFactory;
  
    
  @Test //Check that data source are present
  void contextLoads(ApplicationContext context) throws SQLException
  {
    assertNotNull(context); //check context ok
    assertEquals(((DataSource) context.getBean("userSource")).getConnection().getCatalog(), "useraccount");
    assertEquals(((DataSource) context.getBean("dataSource")).getConnection().getCatalog(), "data"); 
  }    
}
