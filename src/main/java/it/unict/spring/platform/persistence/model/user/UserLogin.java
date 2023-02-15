package it.unict.spring.platform.persistence.model.user;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


@Entity(name="userlogin")
@Table(name = "userlogin", catalog = "useraccount")
@Data
public class UserLogin implements Serializable
{
    @Id
    //@GeneratedValue(strategy = GenerationType.TABLE)
    private Long user_id;
    private int failCount;
    private Timestamp lastFailDate;
    private Timestamp lastSuccessDate;
    
    
    public UserLogin()
    {
      super();
      failCount=0;
      lastFailDate=null;
    }
    
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Setter(AccessLevel.NONE)
    private UserAccount user;
    
    public void setUser(UserAccount user)
    {
      this.user = user;
      this.user_id = user.getId();    
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
         
        if (!(obj instanceof UserLogin)) 
            return false;
         
        return this.user_id != null && this.user_id.equals(((UserLogin) obj).getUser_id())
                               && this.failCount==(((UserLogin) obj).getFailCount())
                               && this.lastFailDate.equals(((UserLogin) obj).getLastFailDate());
   }
}