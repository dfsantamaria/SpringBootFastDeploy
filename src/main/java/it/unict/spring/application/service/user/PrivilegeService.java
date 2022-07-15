package it.unict.spring.application.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.serviceinterface.user.PrivilegeServiceInterface;
import it.unict.spring.application.persistence.model.user.Privilege;
import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.persistence.repository.user.PrivilegeRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService implements PrivilegeServiceInterface
{
    @Autowired
    PrivilegeRepository repository;

           
    @Override
    public List<Privilege> findAll()
    {

        return (List<Privilege>) repository.findAll();
    }
    
    @Override
    public List<Privilege> findByName(String name)
    {
      return repository.findByName(name);
    }
    
    @Override
    @Transactional
    public Privilege save (Privilege g)
    {
      return repository.save(g);
    }
    
    @Override
    @Transactional
    public void delete(Privilege privilege)
    {
      repository.delete(privilege);
    }
    
    @Override
    @Transactional
    public Privilege getOrSetPrivilege(String privName)
  {
       List<Privilege> privileges = repository.findByName(privName);
       Privilege priv;
       if (privileges.isEmpty())
       {
            priv = new Privilege(privName);
            repository.save(priv);            
        }
       else 
           priv=privileges.get(0);
       return priv;
  }
    
  @Override  
  @Transactional
  public  Privilege getOrSetAdminPrivilege()
  {
    return getOrSetPrivilege("ROLE_ADMIN");
  }
  
  @Override  
  @Transactional
  public  Privilege getOrSetSuperAdminPrivilege()
  {
    return getOrSetPrivilege("ROLE_SUPERADMIN");
  }
  
  @Override  
  @Transactional
  public  Privilege getOrSetStaffPrivilege()
  {
    return getOrSetPrivilege("ROLE_STAFF");
  }
  
  @Override  
  @Transactional
  public  Privilege getOrSetStandardUserPrivilege()
  {
    return getOrSetPrivilege("ROLE_STANDARDUSER");
  }

  @Override
  @Transactional
  public void addUserToPrivilege(UserAccount user, Privilege priv)
  {
    priv.addUser(user);
    repository.save(priv);
  }

   @Override
   @Transactional
    public void removeUserFromPrivilege(UserAccount user, Privilege priv)
    {
      priv.deleteUser(user);
      this.save(priv);
    }

   
  
}