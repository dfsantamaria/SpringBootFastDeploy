package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.dto.user.OrganizationDTO;
import it.unict.spring.platform.serviceinterface.user.OrganizationServiceInterface;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.repository.user.OrganizationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OrganizationService implements OrganizationServiceInterface
{
    @Autowired
    OrganizationRepository repository;

           
    @Override
    @Transactional
    public List<Organization> findAll()
    {
        return (List<Organization>) repository.findAll();
    }
    
    @Override
    @Transactional
    public List<Organization> findByName(String name)
    { 
      return repository.findAllByName(name);
    }
    
    @Override
    @Transactional
    public Organization save (Organization g)
    {
      return repository.save(g);
    }
       
    @Override
    @Transactional
    public void delete(Organization organization)
    {
      repository.delete(organization);
    }    
    
    
    /*
    * Retrieve an organization with the given name
    */
    @Override
    @Transactional
    public Organization getOrSetOrganization(String organization)
    {
        
       Optional<Organization> org=repository.findOneByName(organization);
       if (org.isEmpty())      
          return new Organization(organization);      
       else 
          return org.get();
    }
    
    /*
    * From the give DTO to organization
    */
    @Override
    @Transactional
    public Organization mapFromOrganization(OrganizationDTO orgdto)
    {      
       return this.getOrSetOrganization(orgdto.getName());
    }
    
}