package it.unict.spring.platform.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.dto.user.UserAccountDTO;
import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.exception.user.UserAccountAlreadyVerified;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserServiceInterface
{
    List<UserAccount> findAll();
    Optional<UserAccount> findByUsername(String username);
    Optional<UserAccount> findByMail(String email);
    List<UserAccount> findByMailOrUsername(String namemail);
    List<UserAccount> findByMailOrUsername(String namemail, String username);
    UserAccount findById(Long id);
    
    void setSuspended(UserAccount user, boolean suspended);
    
    UserAccount save (UserAccount g);  
    void delete(UserAccount user);
    void deleteUser(UserAccount user);
            
    boolean comparePassword(String hash, String plain);
    String encodePassword(String password);
    UserAccount getUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization, Privilege priv) throws MultipleUsersFoundException;
    UserAccount getSuperAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire ,String organization) throws MultipleUsersFoundException;
    UserAccount getStandardUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException;
    
    UserRegister findRegisterFromCustomUserDetail(CustomUserDetails userdetails);
    Organization findOrganizationFromCustomUserDetails(CustomUserDetails userdetails);
    
    UserAccount mapFromUserDTO(UserAccountDTO userdto, Timestamp accountExpire, Timestamp credentialExpire, UserRegister register, Organization organization) throws MultipleUsersFoundException;
    
    void addRegisterToUser(UserRegister register, UserAccount account);
    void addLoginToUser(UserLogin login, UserAccount user);
    public void createLoginInfo(UserAccount user);
    void setEnabled(UserAccount user, boolean enabled);
   
    
    void sendRegistrationMail(UserAccount user, String url);
    void sendRecoverPasswordMail(UserAccount user, String url);
    
    SecureToken assignTokenToUser(UserAccount user, String type);
    boolean verifyRegistrationToken(String token) throws UserAccountAlreadyVerified;
    boolean verifyPasswordChangedToken(String token, String password);
    
  //  boolean hasUserPrivilege(String privilege);
  //  String getUserByEmail(String mail) throws UserNotFoundException;
 //   

 //   void addTokenToUser(SecureToken token, UserAccount user);
 //   void addOrganizationToUser(Organization org, UserAccount user);
 //   void addPrivilegeToUser(Privilege privilege, UserAccount user);

    
 //   UserAccount getAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException;
    
  //  UserAccount getStaffUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException;
   
 //  //   UserAccount getStandardUser(UserAccount user);
   
 //   
//   
        
 //   
 //   
    
//    
}
