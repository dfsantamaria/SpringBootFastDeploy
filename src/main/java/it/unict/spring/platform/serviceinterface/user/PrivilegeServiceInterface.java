package it.unict.spring.platform.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.Privilege;
import java.util.List;


public interface PrivilegeServiceInterface
{
    List<Privilege> findAll();
    List<Privilege> findByName(String name);
    Privilege save(Privilege privilege);
    void startUpPrivileges();//   Persist the default user roles    
    Privilege getPrivilege(String privName); // Retrieve the user role given in input
    Privilege getSuperAdminPrivilege(); //Retrieve the superadmin user role
    Privilege getStandardUserPrivilege();   
//  Privilege getAdminPrivilege(); 
    
 //   Privilege getStaffPrivilege();
 //   
 //   
    void delete(Privilege privilege);
}
