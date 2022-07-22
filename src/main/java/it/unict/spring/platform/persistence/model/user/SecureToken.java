/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

@Entity
@Table(name = "secureToken", catalog = "useraccount")
public class SecureToken implements Serializable
{
    @EmbeddedId
    public SecureTokenId tokenId= new SecureTokenId();

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
    @JoinColumn(name = "user_id", referencedColumnName ="id")
    @ManyToOne    
    private UserAccount user;

    @Transient
    private boolean isExpired;
  
    public SecureToken()
    {
      super();
    }
          
    
    public SecureToken(String token, String tokenType, Timestamp timestamp, Timestamp expire)
    {
       super();
       this.token=token;       
       this.tokenId.setTokenType(tokenType);
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
      this.tokenId.setTokenId(user.getId());
    }   
    
    public boolean isConsumed()
    {
      return this.isConsumed!=null;
    }
    
    public void setIsConsumed(Timestamp consumed)
    {
      this.isConsumed=consumed;
    }
   
    
}