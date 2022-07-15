package it.unict.spring.application.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.dto.user.OrganizationDTO;
import it.unict.spring.application.dto.user.UserAccountDTO;
import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.exception.user.UserNotFoundException;
import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.persistence.model.user.Privilege;
import it.unict.spring.application.serviceinterface.user.UserServiceInterface;
import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.persistence.repository.user.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        UserAccount user=new UserAccount();
        if(users.size()>1)
            throw new MultipleUsersFoundException("Combination of username and password gets multiple users: "+ username + ", "+mail);
        else if(users.size()==1)
             user=users.get(0);
        else if(users.isEmpty())        
           {
            Organization org = organizationService.getOrSetOrganization(organization); 
            user.setUsername(username);
            user.setPassword(getPasswordEncoder.encode(password));
            user.setMail(mail);                    
            user.addOrganization(org);
            org.addUser(user);
            user.addPrivileges(priv);  
            priv.addUser(user);
            repository.save(user);                    
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
      user.addPrivileges(priv);
      repository.save(user);
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
 
  
  
}