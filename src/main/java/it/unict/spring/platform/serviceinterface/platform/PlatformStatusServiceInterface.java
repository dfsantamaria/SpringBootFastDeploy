package it.unict.spring.platform.serviceinterface.platform;

import it.unict.spring.platform.persistence.model.platform.PlatformStatus;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

public interface PlatformStatusServiceInterface
{
    public PlatformStatus save (PlatformStatus status);
    public void delete(PlatformStatus status);
    public Optional<PlatformStatus> findOneById(Long id);    
    public List<PlatformStatus> findAll();
    public boolean isStatusDefined();
    public PlatformStatus getCurrentStatus();
    public boolean toggleMaintenanceMode();
    public boolean isMaintenanceMode();
    
}
