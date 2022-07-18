/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "secureToken", catalog = "useraccount")
public class SecureToken implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @Column(updatable = false)
    private String tokenType;
    
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp timestamp;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime expireAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="id")
    private UserAccount user;

    @Transient
    private boolean isExpired;
  
    public SecureToken()
    {
      super();
    }
    
      
    
    public SecureToken(String token, String tokenType, Timestamp timestamp, LocalDateTime expire)
    {
       super();
       this.token=token;
       this.tokenType=tokenType;
       this.timestamp=timestamp;
       this.expireAt=expire;      
       this.isExpired=false;       
    }
    
    public boolean isExpired()
    {
        return getExpireAt().isBefore(LocalDateTime.now()); // this is generic implementation, you can always make it timezone specific
    }
   
    public Long getId()
    {
      return this.id;
    }
    
    public Timestamp getTimestamp()
    {
     return this.timestamp;
    }
    
    public String getTokenType()
    {
        return this.tokenType;
    }
            
    public LocalDateTime getExpireAt()
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
    
}