package it.unict.spring.platform.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
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

@Entity(name="SecureToken")
@Table(name = "secureToken", catalog = "useraccount")
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
   
    public SecureTokenId getId()
    {
      return this.tokenId;
    }
    
    public Timestamp getTimestamp()
    {
     return this.timestamp;
    }
    
    public String getTokenType()
    {
        return this.tokenId.getTokenType();
    }
        
    public void setTokenType(String tokenType)
    {
       this.token=tokenType;
    }
    
    public Timestamp getExpireAt()
    {
      return this.expireAt;
    }
    
    public String getToken()
    {
     return this.token;
    }
    
    public void addUser(UserAccount user)
    {
      this.user=user;      
    }   
    
    public boolean isConsumed()
    {
      return this.isConsumed!=null;
    }
    
    public void setIsConsumed(Timestamp consumed)
    {
      this.isConsumed=consumed;
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
         
        return this.getId().getTokenId() != null && this.getId().getTokenType()!=null &&
                this.getId().getTokenId().equals(((SecureToken) obj).getId().getTokenId()) &&
                this.getId().getTokenType().equals(((SecureToken) obj).getId().getTokenType());
   }
}