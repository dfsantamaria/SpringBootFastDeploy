package it.unict.spring.platform.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.dto.user.OrganizationDTO;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import java.util.List;


public interface OrganizationServiceInterface
{
    List<Organization> findAll();
    List<Organization> findByName(String name);
    
    Organization save(Organization privilege);
    Organization getOrSetOrganization(String organization); 
    Organization getOrSetOrganization(Organization organization);
    Organization mapFromOrganization(OrganizationDTO orgdto);
    void addUserToOrganization(UserAccount user, Organization org);
    void removeUserFromOrganization(UserAccount user, Organization org);
    void delete(Organization organization);
}
