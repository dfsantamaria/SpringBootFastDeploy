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

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


@Entity(name="UserLogin")
@Table(name = "userlogin", catalog = "useraccount")
public class UserLogin implements Serializable
{
    @Id
    private Long id;
    private int failCount;
    private Timestamp lastFailDate;
    
    
    public UserLogin()
    {
      super();
      failCount=0;
      lastFailDate=null;
    }
    
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user;

public UserAccount getUser()
    {
     return user;
    }
    
    public void setUser(UserAccount user)
    {
      this.user = user;
      this.id = user.getId();    
    }

    public Long getId()
    {
        return id;
    }
    
    public int getFailCount()
    {
      return this.failCount;
    }
    
    public void setFailCount(int count)
    {
      this.failCount=count;
    }
    
       
    public Timestamp getLastFailDate()
    {
      return this.lastFailDate;
    }
    
    public void setLastFailDate(Timestamp fail)
    {
      this.lastFailDate=fail;
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
         
        return this.id != null && this.id.equals(((UserLogin) obj).getId())
                               && this.failCount==(((UserLogin) obj).getFailCount())
                               && this.lastFailDate.equals(((UserLogin) obj).getLastFailDate());
   }
}