package it.unict.spring.platform.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class SecureTokenId implements Serializable
{
    @Column(name="tokenType")
    private String tokenType;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;
    
    public SecureTokenId()
    {
        super();
    }   
   
    public SecureTokenId(UserAccount user, String tokenType)
    {
        this.user=user;
        this.tokenType=tokenType;
    }
    
    @Override
    public boolean equals(Object o)
    {     
        if (o == null || getClass() != o.getClass())
            return false;    
        SecureTokenId that = (SecureTokenId) o;
        return Objects.equals(this.tokenType, that.tokenType) &&
                    Objects.equals(this.user.getId(), that.user.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(tokenType, this.user.getId());
    }
    
    public void setTokenType(String tokenType)
    {
      this.tokenType=tokenType;
    }
    
    public String getTokenType()
    {
      return this.tokenType;
    }
    
   
    public void setUser(UserAccount user)
    {
     this.user=user;
    }
            
    public UserAccount getUser()
    {
      return this.user;
    }
    
    public Long getTokenId()
    {
      return this.user.getId();
    }
    
        
}
