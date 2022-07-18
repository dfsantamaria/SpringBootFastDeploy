package it.unict.spring.application.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.dto.user.UserAccountDTO;
import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.exception.user.UserNotFoundException;
import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.persistence.model.user.Privilege;
import it.unict.spring.application.persistence.model.user.SecureToken;
import it.unict.spring.application.serviceinterface.user.UserServiceInterface;
import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.persistence.model.user.UserRegister;
import it.unict.spring.application.persistence.repository.user.UserRepository;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserAccount getOrSetAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetAdminPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);
    }
    
    
    @Transactional
    private UserAccount getOrSetUser(String username, String password, String mail, String organization, Privilege priv) throws MultipleUsersFoundException
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
            user=new UserAccount(username,getPasswordEncoder.encode(password), mail);                            
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
    public UserAccount getOrSetStaffUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetStaffPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);    
    }

    @Override
    @Transactional
    public UserAccount getOrSetStandardUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetStandardUserPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);       
    }
    
    @Override
    @Transactional
    public UserAccount getOrSetStandardUser(UserAccount user)
    {
      Privilege priv= privilegeService.getOrSetStandardUserPrivilege();
      user.getOrganization().stream().forEach(org-> 
                                                   organizationService.getOrSetOrganization(org)                                                   
                                            );
      this.addPrivilegeToUser(priv, user);
      this.save(user);
      return user;
    }

    @Override
    @Transactional
    public UserAccount getOrSetSuperAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetSuperAdminPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);       
    }

    @Override
    @Transactional
    public UserAccount mapFromUserDTO(UserAccountDTO userdto, Organization organization) throws MultipleUsersFoundException
    {        
      return this.getOrSetStandardUser(userdto.getUsername(),
                    userdto.getPassword(),
                    userdto.getMail(),
                    organization.getName());
       
    }

    @Override
    @Transactional
    public void sendRegistrationMail(UserAccount user)
    {
      SecureToken token = secureTokenService.generateToken("FReg");
      secureTokenService.addUserToToken(user, token);
      this.addTokenToUser(token, user);
      this.save(user);
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
    @Transactional
    public void addRegisterToUser(UserRegister register, UserAccount user)
    {
      user.setRegister(register);      
    }
    
  
    @Override
    public void addTokenToUser(SecureToken token, UserAccount user)
    {
       user.addSecureToken(token);
    }
  
  
}