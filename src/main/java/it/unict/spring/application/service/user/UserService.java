package it.unict.spring.application.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.exception.user.UserNotFoundException;
import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.persistence.model.user.Privilege;
import it.unict.spring.application.serviceinterface.user.UserServiceInterface;
import it.unict.spring.application.persistence.model.user.Users;
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
    PasswordEncoder getPasswordEncoder;
           
    @Override
    public List<Users> findAll()
    {
        return (List<Users>) repository.findAll();
    }
    
    @Override
    public List<Users> findByUsername(String name)
    {
      return repository.findByUsername(name);
    }
    
    @Override   
    @Transactional
    public Users save (Users g)
    {
      return repository.save(g);
    }
    
    @Override
    @Transactional
    public void delete(Users user)
    {
      repository.delete(user);
    }
    
    @Override
    public List<Users> findByMail(String email)
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
        List<Users> users = repository.findByMail(mail);
        if(users.isEmpty())        
              throw new UserNotFoundException("user not found using email "+ mail); 
        else
            return users.get(0).getMail();
    }

    @Override 
    @Transactional
    public Users getOrSetAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetAdminPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);
    }
    
    
    @Transactional
    private Users getOrSetUser(String username, String password, String mail, String organization, Privilege priv) throws MultipleUsersFoundException
    {         
        List<Users> users = repository.findByMail(mail);    
        users.addAll(repository.findByUsername(username));
        Users user=new Users();
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
    public Users getOrSetStaffUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetStaffPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);    
    }

    @Override
    @Transactional
    public Users getOrSetStandardUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetStandardUserPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);       
    }

    @Override
    @Transactional
    public Users getOrSetSuperAdminUser(String username, String password, String mail, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getOrSetSuperAdminPrivilege(); 
      return this.getOrSetUser(username, password, mail, organization, priv);       
    }
  
  
  
}