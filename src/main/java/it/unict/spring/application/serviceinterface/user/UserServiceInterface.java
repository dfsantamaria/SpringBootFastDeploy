package it.unict.spring.application.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.dto.user.OrganizationDTO;
import it.unict.spring.application.dto.user.UserAccountDTO;
import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.exception.user.UserNotFoundException;
import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.persistence.model.user.UserAccount;
import java.util.List;


public interface UserServiceInterface
{
    List<UserAccount> findAll();
    List<UserAccount> findByUsername(String username);
    List<UserAccount> findByMail(String email);
    UserAccount save (UserAccount g);  
    void delete(UserAccount user);
    void setEnabled(UserAccount user, boolean enabled);
    boolean hasUserPrivilege(String privilege);
    String getUserByEmail(String mail) throws UserNotFoundException;
   
    UserAccount getOrSetAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
    
    UserAccount getOrSetStaffUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
   
    UserAccount getOrSetStandardUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
    UserAccount getOrSetStandardUser(UserAccount user);
   
    UserAccount getOrSetSuperAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
    
    UserAccount mapFromUserDTO(UserAccountDTO userdto, Organization organization) throws MultipleUsersFoundException;
    
}
