package it.unict.spring.platform.service.platform;

import it.unict.spring.platform.persistence.model.platform.PlatformStatus;
import it.unict.spring.platform.persistence.repository.platform.PlatformStatusRepository;
import org.springframework.stereotype.Service;
import it.unict.spring.platform.serviceinterface.platform.PlatformStatusServiceInterface;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

@Service
public class PlatformStatusService implements PlatformStatusServiceInterface
{
   @Autowired  
   PlatformStatusRepository repository;
   
   @Override
   @Transactional
   public PlatformStatus save (PlatformStatus g)
   {
     return repository.save(g);
   }
   
    @Override
    @Transactional
    public void delete(PlatformStatus status)
    {
      repository.delete(status);
    }
   
   @Override
   @Transactional
   public Optional<PlatformStatus> findOneById(Long id)
   {
     return repository.findOneById(id);
   }
   
   @Override
   @Transactional
   public List<PlatformStatus> findAll()
   {
     return repository.findAll();
   }
   
   @Override
   @Transactional
   public boolean isStatusDefined()
   {
     return repository.count()>0;
   }
   
   @Override
   @Transactional
   public PlatformStatus getCurrentStatus()
   {
     List<PlatformStatus> status = repository.findTop1ByOrderByIdAsc();
     if(status.isEmpty())
         return null;
     return status.get(0);
   }
   
   @Override
   @Transactional
   public boolean toggleMaintenanceMode()
   {
     PlatformStatus status = this.getCurrentStatus();
     boolean newStatus = !status.isMaintenanceMode();
     status.setMaintenanceMode(newStatus);
     this.save(status);
     return newStatus;
   }
   
   @Override
   @Transactional
   public boolean isMaintenanceMode()
   {
     return this.getCurrentStatus().isMaintenanceMode();
   }
}
