package it.unict.spring.application.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.persistence.model.user.Privilege;
import it.unict.spring.application.persistence.model.user.UserAccount;
import java.util.List;


public interface PrivilegeServiceInterface
{
    List<Privilege> findAll();
    List<Privilege> findByName(String name);
    Privilege save(Privilege privilege);
    Privilege getPrivilege(String privName);
    Privilege getAdminPrivilege(); 
    Privilege getSuperAdminPrivilege();
    Privilege getStaffPrivilege();
    Privilege getStandardUserPrivilege();
    void addUserToPrivilege(UserAccount user, Privilege priv);
    void removeUserFromPrivilege(UserAccount user, Privilege priv);
    void startUpPrivileges();
    void delete(Privilege privilege);
}
