package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.serviceinterface.user.PrivilegeServiceInterface;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.repository.user.PrivilegeRepository;
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
  public Privilege getPrivilege(String privName)
  {
       List<Privilege> privileges = repository.findByName(privName);
       Privilege priv;
       if (privileges.isEmpty())
       {
            return null;        
        }
       else 
           priv=privileges.get(0);
       return priv;
  }  
    
  @Transactional
  private Privilege getOrSetPrivilege(String privName)
  {
       List<Privilege> privileges = repository.findByName(privName);
       Privilege priv;
       if (privileges.isEmpty())
       {
            priv = new Privilege(privName);
            this.save(priv);            
        }
       else 
           priv=privileges.get(0);
       return priv;
  }
    
    
  @Transactional
  private  Privilege getOrSetAdminPrivilege()
  {
    return getOrSetPrivilege("ROLE_ADMIN");
  }
  
  
  @Transactional
  private  Privilege getOrSetSuperAdminPrivilege()
  {
    return getOrSetPrivilege("ROLE_SUPERADMIN");
  }
  
   
  @Transactional
  private  Privilege getOrSetStaffPrivilege()
  {
    return getOrSetPrivilege("ROLE_STAFF");
  }
  
   
  @Transactional
  private Privilege getOrSetStandardUserPrivilege()
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

   @Override
   @Transactional
   public void startUpPrivileges()
   {
      this.getOrSetSuperAdminPrivilege();
      this.getOrSetAdminPrivilege();
      this.getOrSetStaffPrivilege();
      this.getOrSetStandardUserPrivilege();     
   }
  
  @Override 
  @Transactional
  public  Privilege getAdminPrivilege()
  {
    return getPrivilege("ROLE_ADMIN");
  }
  
  
  @Override
  @Transactional
  public  Privilege getSuperAdminPrivilege()
  {
    return getPrivilege("ROLE_SUPERADMIN");
  }
  
   
  @Override
  @Transactional
  public  Privilege getStaffPrivilege()
  {
    return getPrivilege("ROLE_STAFF");
  }
  
  @Override 
  @Transactional
  public Privilege getStandardUserPrivilege()
  {
    return getPrivilege("ROLE_STANDARDUSER");
  }
  
}