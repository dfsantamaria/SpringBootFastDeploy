package it.unict.spring.application.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.persistence.model.user.Organization;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> 
{
    List<Organization> findByName(String name);
    @Override
    List<Organization> findAll();
    @Override 
    Organization save(Organization organization); 
    @Override
    void delete(Organization todo);
    
}
    

