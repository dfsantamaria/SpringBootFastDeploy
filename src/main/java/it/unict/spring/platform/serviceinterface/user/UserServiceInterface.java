package it.unict.spring.platform.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.dto.user.UserAccountDTO;
import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.exception.user.UserNotFoundException;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
   
    void addTokenToUser(SecureToken token, UserAccount user);
    void addOrganizationToUser(Organization org, UserAccount user);
    void addPrivilegeToUser(Privilege privilege, UserAccount user);
    
    UserAccount getUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization, Privilege priv) throws MultipleUsersFoundException;

    
    UserAccount getAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException;
    
    UserAccount getStaffUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException;
   
    UserAccount getStandardUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException;
    UserAccount getStandardUser(UserAccount user);
   
    UserAccount getSuperAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire ,String organization) throws MultipleUsersFoundException;
    
    UserAccount mapFromUserDTO(UserAccountDTO userdto, Timestamp accountExpire, Timestamp credentialExpire, UserRegister register, Organization organization) throws MultipleUsersFoundException;
    
    
    //void setUserRegister(UserRegister register, UserAccount user);
    void addRegisterToUser(UserRegister register, UserAccount user);
    
    void sendRegistrationMail(UserAccount user);
}
