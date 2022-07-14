package it.unict.spring.application.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.exception.user.UserNotFoundException;
import it.unict.spring.application.persistence.model.user.Users;
import java.util.List;


public interface UserServiceInterface
{
    List<Users> findAll();
    List<Users> findByUsername(String username);
    List<Users> findByMail(String email);
    Users save (Users g);  
    void delete(Users user);
    void setEnabled(Users user, boolean enabled);
    boolean hasUserPrivilege(String privilege);
    String getUserByEmail(String mail) throws UserNotFoundException;
    Users getOrSetAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
    Users getOrSetStaffUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
    Users getOrSetStandardUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
    Users getOrSetSuperAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException;
    
}
