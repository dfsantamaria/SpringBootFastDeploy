package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.dto.user.UserAccountDTO;
import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.exception.user.UserAccountAlreadyVerified;
import it.unict.spring.platform.exception.user.UserNotFoundException;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.serviceinterface.user.UserServiceInterface;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.persistence.repository.user.UserRepository;
import it.unict.spring.platform.service.communication.CustomMailService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserServiceInterface
{
    @Autowired
    UserRepository repository;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    SecureTokenService secureTokenService;
    @Autowired
    UserRegisterService registryService;
    @Autowired
    CustomMailService mailService;
    
    @Autowired
    PasswordEncoder getPasswordEncoder;
           
    @Override
    public List<UserAccount> findAll()
    {
        return (List<UserAccount>) repository.findAll();
    }
    
    @Override
    public List<UserAccount> findByUsername(String name)
    {
      return repository.findByUsername(name);
    }
    
    @Override
    @Transactional
    public void setEnabled(UserAccount user, boolean enabled)
    {      
      user.setEnabled(enabled);
      repository.save(user);
    }
            
    @Override   
    @Transactional
    public UserAccount save (UserAccount g)
    {
      return repository.save(g);
    }
            
    @Override
    @Transactional
    public void delete(UserAccount user)
    {
      repository.delete(user);
    }
    
    @Override
    public List<UserAccount> findByMail(String email)
    {
      return repository.findByMail(email);
    }
    
    public UserAccount findById(Long id)
    {
      return repository.findById(id).get();
    }
    
    @Override
    @Transactional
    public boolean hasUserPrivilege(String privilege)
    {
     Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
     SecurityContextHolder.getContext().getAuthentication().getAuthorities();
     boolean hasRole = false;
     for (GrantedAuthority authority : authorities)
     {
      hasRole = authority.getAuthority().equals(privilege);
      if (hasRole)
       break;     
     }
      return hasRole;      
  } 
    
    @Override
    public String getUserByEmail(String mail) throws UserNotFoundException
    {
        List<UserAccount> users = repository.findByMail(mail);
        if(users.isEmpty())        
              throw new UserNotFoundException("user not found using email "+ mail); 
        else
            return users.get(0).getMail();
    }

    @Override 
    @Transactional
    public UserAccount getAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getAdminPrivilege(); 
      return this.getUser(username, password, mail, accountExpire,  credentialExpire, organization, priv);
    }
    
    @Override
    @Transactional
    public UserAccount getUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire,
                               String organization, Privilege priv) throws MultipleUsersFoundException
    {         
        List<UserAccount> users = repository.findByMail(mail);    
        users.addAll(repository.findByUsername(username));
        UserAccount user=null;
        if(users.size()>1)
            throw new MultipleUsersFoundException("Combination of username and password gets multiple users: "+ username + ", "+mail);
        else if(users.size()==1)
             user=users.get(0);
        else if(users.isEmpty())        
           {              
            Organization org = organizationService.getOrSetOrganization(organization); 
            user=new UserAccount(username,getPasswordEncoder.encode(password), mail, accountExpire, credentialExpire);                            
            this.addOrganizationToUser(org,user); //user.addOrganization(org);           
            organizationService.addUserToOrganization(user, org);
            this.addPrivilegeToUser(priv, user); //user.addPrivileges(priv);  
            privilegeService.addUserToPrivilege(user, priv);
            this.save(user);                    
           } 
        return user;       
    }

    @Override
    @Transactional
    public UserAccount getStaffUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getStaffPrivilege(); 
      return this.getUser(username, password, mail, accountExpire, credentialExpire, organization, priv);    
    }

    @Override
    @Transactional
    public UserAccount getStandardUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getStandardUserPrivilege(); 
      return this.getUser(username, password, mail, accountExpire,  credentialExpire, organization, priv);       
    }
    
    @Override
    @Transactional
    public UserAccount getStandardUser(UserAccount user)
    {
      Privilege priv= privilegeService.getStandardUserPrivilege();
      user.getOrganization().stream().forEach(org-> 
                                                   organizationService.getOrSetOrganization(org)                                                   
                                            );
      this.addPrivilegeToUser(priv, user);
      this.save(user);
      return user;
    }

    @Override
    @Transactional
    public UserAccount getSuperAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getSuperAdminPrivilege(); 
      return this.getUser(username, password, mail, accountExpire, credentialExpire, organization, priv);       
    }

    @Override
    @Transactional
    public UserAccount mapFromUserDTO(UserAccountDTO userdto, Timestamp accountExpire, Timestamp credentialExpire, UserRegister register, Organization organization) throws MultipleUsersFoundException
    {               
      UserAccount user= this.getStandardUser(userdto.getUsername(),
                        userdto.getPassword(),
                        userdto.getMail(), accountExpire, credentialExpire,
                        organization.getName());
      this.setRegister(register, user);
      return user;
    }

    @Override
    @Transactional
    public SecureToken assignTokenToUser(UserAccount user)
    {
      SecureToken token = secureTokenService.generateToken("FReg");
      secureTokenService.addUserToToken(user, token);
      this.addTokenToUser(token, user);
      this.save(user);
      return token;
    }        
    
    @Override    
    public void sendRegistrationMail(UserAccount user, String url)
    {
      SecureToken token=this.assignTokenToUser(user);
      mailService.sendSimpleEmail(user.getMail(), "Confirm registration", url+"/regitrationConfirm?token=" + token.getToken());
     // ;
      
    }

    @Override
    @Transactional
    public void addOrganizationToUser(Organization org, UserAccount user)
    {
        user.addOrganization(org);       
    }
    
    @Override
    @Transactional
    public void addPrivilegeToUser(Privilege priv, UserAccount user)
    {
       user.addPrivileges(priv);       
    }
   
    @Override
    public void addTokenToUser(SecureToken token, UserAccount user)
    {
       user.addSecureToken(token);
    }
    
    
    @Transactional
    @Override
    public void addRegisterToUser(UserRegister register, UserAccount user)
    {
      user.setRegister(register);      
    }
    
    @Override
    @Transactional
    public void setRegister(UserRegister register, UserAccount user)            
    {        
      registryService.setUser(register, user);
      registryService.save(register);
      this.addRegisterToUser(register, user);
      this.save(user);      
    }
    
    @Override
    @Transactional    
    public boolean checkToken(String token) throws UserAccountAlreadyVerified
    {
      SecureToken sec= secureTokenService.findByToken(token).get(0);
      if( sec.getExpireAt().after(Timestamp.valueOf(LocalDateTime.now())))
      {
         UserAccount user=this.findById(sec.getId().getTokenId());
         if(!user.isEnabled() && !sec.isConsumed())
         {
             secureTokenService.consumeToken(sec);
             secureTokenService.save(sec);
             Hibernate.initialize(user);
             user.setEnabled(true);             
             this.save(user);
             return true;
         }
         else throw new UserAccountAlreadyVerified(sec.getToken());
      }
      return false;
    }
  
}