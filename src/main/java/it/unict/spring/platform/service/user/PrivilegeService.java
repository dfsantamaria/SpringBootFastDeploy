package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.serviceinterface.user.PrivilegeServiceInterface;
import it.unict.spring.platform.persistence.model.user.Privilege;
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
    Privilege priv = getOrSetPrivilege("ROLE_SUPERADMIN", "Superadministrator", "Access");
    priv=this.save(priv);
    return priv;
   }
  
   /* 
    * Retrive the user role given in input
    *
   */
  @Transactional
  private Privilege getOrSetPrivilege(String privName, String label, String type)
  {
       List<Privilege> privileges = repository.findByName(privName);
       Privilege priv;
       if (privileges.isEmpty())
       {
            priv = new Privilege(privName, label, type);                       
        }
       else 
           priv=privileges.get(0);
       return priv;
  }
  
  /* 
    * Persist or retrive the admin role 
    *
   */
   @Transactional
   private  Privilege getOrSetAdminPrivilege()
   {
    Privilege priv = this.getOrSetPrivilege("ROLE_ADMIN", "Administrator", "Access");
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
    Privilege priv = getOrSetPrivilege("ROLE_STAFF", "Staff", "Access");
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
    Privilege priv = getOrSetPrivilege("ROLE_STANDARDUSER", "Standard User", "Access");
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
  
  /* 
    * Retrive the admin user role given in input
    *
   */
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
  /*
  
 
  
  
    

      
  

 
  
  @Override 
  @Transactional
  public  Privilege getAdminPrivilege()
  {
    return getPrivilege("ROLE_ADMIN");
  }
  
  
  
  
   
  @Override
  @Transactional
  public  Privilege getStaffPrivilege()
  {
    return getPrivilege("ROLE_STAFF");
  }
  
  */
}