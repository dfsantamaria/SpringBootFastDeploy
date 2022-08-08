package it.unict.spring.platform.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.persistence.model.user.Organization;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> 
{
    List<Organization> findAllByName(String name);
    Optional<Organization> findOneByName(String name);
    @Override
    List<Organization> findAll();
    @Override 
    Organization save(Organization organization); 
    @Override
    void delete(Organization todo);
    
}
    

