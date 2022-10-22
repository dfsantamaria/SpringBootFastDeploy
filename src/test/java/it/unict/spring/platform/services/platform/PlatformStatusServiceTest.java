package it.unict.spring.platform.services.platform;

import it.unict.spring.platform.Application;
import it.unict.spring.platform.service.platform.PlatformStatusService;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import it.unict.spring.platform.persistence.model.platform.PlatformStatus;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlatformStatusServiceTest
{
    @SpyBean
    PlatformStatusService platformService;
    
    private PlatformStatus status;
    
    @Test
    public void testToggleMaintenanceService()
    {
      assertFalse(platformService.isMaintenanceMode());
      assertTrue(platformService.toggleMaintenanceMode());
      assertTrue(platformService.isMaintenanceMode());
      assertFalse(platformService.toggleMaintenanceMode());
      assertFalse(platformService.isMaintenanceMode());
    }
    
  /*
    @BeforeAll
    public void addStatus()
    {
      status=new PlatformStatus(false);
      status=platformService.save(status);
    }
    
    @AfterAll
    public void removeStatus()
    {
      platformService.delete(status);
    }
    */
}
