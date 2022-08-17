package it.unict.spring.platform.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

@Entity(name="SecureToken")
@Table(name = "secureToken", catalog = "useraccount")
@Data
public class SecureToken implements Serializable
{
    @EmbeddedId
    public SecureTokenId tokenId;

    @Column(unique = true)
    private String token;
    
    private Timestamp isConsumed;
    
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp timestamp;

    @Column(updatable = false)
    @Basic(optional = false)
    private Timestamp expireAt;

    @MapsId("user_id")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="id")        
    private UserAccount user;

    @Transient
    private boolean isExpired;
  
    public SecureToken()
    {
      super();
    }
          
    
    public SecureToken(SecureTokenId tokenId, String token, Timestamp timestamp, Timestamp expire)
    {
       super();   
       this.tokenId=tokenId;
       this.token=token;    
       this.timestamp=timestamp;
       this.expireAt=expire;      
       this.isExpired=false;        
    }
    
    public boolean isExpired()
    {
        return getExpireAt().before(Timestamp.valueOf(LocalDateTime.now())); // this is generic implementation, you can always make it timezone specific
    }
   
        
    public void addUser(UserAccount user)
    {
      this.user=user;      
    }   
    
    public boolean isConsumed()
    {
      return this.isConsumed!=null;
    }
    
   
    public void removeUser()
    {
      this.user=null;
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
         
        if (!(obj instanceof SecureToken)) 
            return false;
         
        return this.getTokenId().getTokenId() != null && this.getTokenId().getTokenType()!=null &&
                this.getTokenId().getTokenId().equals(((SecureToken) obj).getTokenId().getTokenId()) &&
                this.getTokenId().getTokenType().equals(((SecureToken) obj).getTokenId().getTokenType());
   }
}