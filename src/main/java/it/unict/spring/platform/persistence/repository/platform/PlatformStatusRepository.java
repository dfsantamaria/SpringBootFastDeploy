package it.unict.spring.platform.persistence.repository.platform;

import it.unict.spring.platform.persistence.model.platform.PlatformStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

@Repository
public interface PlatformStatusRepository extends JpaRepository<PlatformStatus, Long> 
{
  Optional<PlatformStatus> findOneById(Long id);    
  @Override
  List<PlatformStatus> findAll();
  @Override
  PlatformStatus save(PlatformStatus status);
  @Override
  void delete(PlatformStatus status);
  List<PlatformStatus> findTop1ByOrderByIdAsc();
}