package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.serviceinterface.user.PrivilegeServiceInterface;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.repository.user.PrivilegeRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService implements PrivilegeServiceInterface
{
    @Autowired
    PrivilegeRepository repository;

           
    @Override
    @Transactional
    public List<Privilege> findAll()
    {

        return (List<Privilege>) repository.findAll();
    }
    
    @Override
    @Transactional
    public Optional<Privilege> findByName(String name)
    {
      return repository.findOneByName(name);
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
    
   /*
     Persist the default user roles
    */ 
   @Override
   @Transactional
   public void startUpPrivileges()
   {
      this.getOrSetSuperAdminPrivilege();
      this.getOrSetAdminPrivilege();
      this.getOrSetStaffPrivilege();
      this.getOrSetStandardUserPrivilege();     
   }
   
   /*
     Persist or retrieve the superadmin user role
   */ 
   @Transactional
   private  Privilege getOrSetSuperAdminPrivilege()
   {
    Privilege priv = getOrSetPrivilege("ROLE_SUPERADMIN", "Superadministrator", "Access", 1.0);
    priv=this.save(priv);
    return priv;
   }
  
   /* 
    * Retrive the user role given in input
    *
   */
  @Transactional
  private Privilege getOrSetPrivilege(String privName, String label, String type, double priority)
  {
       Optional<Privilege> privileges = this.findByName(privName);
       Privilege priv;
       if (privileges.isEmpty())
       {
            priv = new Privilege(privName, label, type, priority);                       
        }
       else 
           priv=privileges.get();
       return priv;
  }
  
  /* 
    * Persist or retrive the admin role 
    *
   */
   @Transactional
   private  Privilege getOrSetAdminPrivilege()
   {
    Privilege priv = this.getOrSetPrivilege("ROLE_ADMIN", "Administrator", "Access", 2.0);
    priv=this.save(priv);
    return priv;
   }
  
   /* 
    * Persist or retrive the staff role 
    *
   */
   @Transactional
   private  Privilege getOrSetStaffPrivilege()
   {
    Privilege priv = getOrSetPrivilege("ROLE_STAFF", "Staff", "Access", 3.0);
    priv=this.save(priv);
    return priv;
  }
   
   /* 
    * Persist or retrive the standard user role 
    *
   */   
  @Transactional
  private Privilege getOrSetStandardUserPrivilege()
  {
    Privilege priv = getOrSetPrivilege("ROLE_STANDARDUSER", "Standard User", "Access", 4.0);
    priv=this.save(priv);
    return priv;
  }
   
  /* 
    * Return the superadmin user role
    *
   */
  @Override
  @Transactional
  public  Privilege getSuperAdminPrivilege()
  {
    return this.getPrivilege("ROLE_SUPERADMIN");
  }
  
  
  @Override 
  @Transactional
  public Privilege getStandardUserPrivilege()
  {
    return getPrivilege("ROLE_STANDARDUSER");
  }
  
  @Override 
  @Transactional
  public Privilege getStaffUserPrivilege()
  {
    return getPrivilege("ROLE_STAFF");
  }
  
  /* 
    * Retrive the admin user role given in input
    *
   */
  @Override
  @Transactional
  public Privilege getPrivilege(String privName)
  {
       Optional<Privilege> privileges = this.findByName(privName);
       Privilege priv;
       if (privileges.isEmpty())
       {
            return null;        
        }
       else 
           priv=privileges.get();
       return priv;
  }  
  
  @Override
  @Transactional
  public  void upgradeUserPrivilege(UserAccount user, Privilege newprivilege)
    {
      for(Privilege privilege: user.getPrivileges())
      {
        if(privilege.getType().equals("Access") && privilege.getPriority()!=newprivilege.getPriority())
        {
          user.removePrivileges(privilege);
          break;
        }
      }    
      user.addPrivileges(newprivilege);       
    }
  @Override
  public List<Privilege> findPrivilegeBetweenPriority(String type, Double priorityStart, Double priorityEnd)
  {
    return repository.findAllByTypeAndPriorityLessThanAndPriorityGreaterThan(type, priorityStart, priorityEnd);
  }

    @Override
    public Optional<Privilege> findUserPrivileges(Long id, String access)
    {
      return repository.findOneByUsers_IdAndType(id, "Access");
    }
}