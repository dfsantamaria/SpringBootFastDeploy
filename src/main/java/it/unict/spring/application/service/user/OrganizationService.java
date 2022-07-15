package it.unict.spring.application.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.dto.user.OrganizationDTO;
import it.unict.spring.application.serviceinterface.user.OrganizationServiceInterface;
import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.persistence.repository.user.OrganizationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OrganizationService implements OrganizationServiceInterface
{
    @Autowired
    OrganizationRepository repository;

           
    @Override
    public List<Organization> findAll()
    {
        return (List<Organization>) repository.findAll();
    }
    
    @Override
    public List<Organization> findByName(String name)
    { 
      return repository.findByName(name);
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
    
    @Override
    @Transactional
    public Organization getOrSetOrganization(String organization)
    {
       List<Organization> orgs = repository.findByName(organization);
       Organization org;
       if (orgs.isEmpty())
       {
          org = new Organization(organization);
          repository.save(org);
       }
       else org=orgs.get(0);
       return org;
    }
    
    @Override
    @Transactional
    public Organization getOrSetOrganization(Organization organization)
    {
       List<Organization> orgs = repository.findByName(organization.getName());
       Organization org;
       if (orgs.isEmpty())
       {    
          org=organization; 
          repository.save(org);
          
       }
       else org=orgs.get(0);
       return org;
    }
    
    @Override
    @Transactional
    public Organization mapFromOrganization(OrganizationDTO orgdto)
    {      
       return this.getOrSetOrganization(orgdto.getName());
    }
}