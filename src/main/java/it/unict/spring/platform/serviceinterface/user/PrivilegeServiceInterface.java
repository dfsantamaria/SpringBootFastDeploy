package it.unict.spring.platform.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import java.util.List;
import java.util.Optional;


public interface PrivilegeServiceInterface
{
    List<Privilege> findAll();
    Optional<Privilege> findByName(String name);
    Privilege save(Privilege privilege);
    void startUpPrivileges();//   Persist the default user roles    
    Privilege getPrivilege(String privName); // Retrieve the user role given in input
    Privilege getSuperAdminPrivilege(); //Retrieve the superadmin user role
    Privilege getStandardUserPrivilege(); 
    Privilege getStaffUserPrivilege();
    void upgradeUserPrivilege(UserAccount user, Privilege newprivilege);
    void delete(Privilege privilege);
    List<Privilege> findPrivilegeBetweenPriority(String type, Double priorityStart, Double priorityEnd);
    Optional<Privilege> findUserPrivileges(Long id, String access);
}
