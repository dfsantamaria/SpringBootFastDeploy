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
import lombok.Data;
import lombok.AccessLevel;
import lombok.Setter;

@Entity(name="UserRegister")
@Table(name = "userregistry", catalog = "useraccount")
@Data
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
    @Setter(AccessLevel.NONE)
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
   
    public void setUser(UserAccount user)
    {
      this.user = user;
      this.id =user.getId();    
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
                               && this.firstname.equals(((UserRegister) obj).getFirstname())
                               && this.middlename.equals(((UserRegister) obj).getMiddlename())
                               && this.lastname.equals(((UserRegister) obj).getLastname());
                
   }
    
}