package it.unict.spring.platform.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="UserRegister")
@Table(name = "userregistry", catalog = "useraccount")
public class UserRegister implements Serializable
{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstname;
    private String middlename;
    private String lastname;
    
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user;
    
    public UserRegister()
    {
       super();
    }
    
    public UserRegister(String name, String middlename, String lastname)
    {
       super();
       this.firstname=name;       
       this.middlename=middlename;
       this.lastname=lastname;
       user=null;
    }
    
    public UserAccount getUser()
    {
     return user;
    }
    
    public void setUser(UserAccount user)
    {
      this.user = user;
      this.id =user.getId();    
    }

    public Long getId() {
        return id;
    }
        
    public String getFirstName()
    {
      return this.firstname;
    }
    
    public void setFirstName(String name)
    {
      this.firstname=name;
    }
    
    public String getMiddleName()
    {
      return this.middlename;
    }
    
    public void setMiddleName(String name)
    {
      this.middlename=name;
    }
    
    public String getLastName()
    {
      return this.lastname;
    }
    
    public void setLastName(String name)
    {
      this.lastname=name;
    }
    
    @Override
    public int hashCode() 
    {        
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
         
        if (!(obj instanceof UserRegister)) 
            return false;
         
        return this.id != null && this.id.equals(((UserRegister) obj).getId())
                               && this.firstname.equals(((UserRegister) obj).getFirstName())
                               && this.middlename.equals(((UserRegister) obj).getMiddleName())
                               && this.lastname.equals(((UserRegister) obj).getLastName());
                
   }
    
}