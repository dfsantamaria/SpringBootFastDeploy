package it.unict.spring.application.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class SecureTokenId implements Serializable
{
    @Column(name="tokenType")
    private String tokenType;
    @Column(name ="user_id")
    private Long user_id;
    
    public SecureTokenId()
    {
        super();
    }   
   
    public SecureTokenId(Long user_id, String tokenType)
    {
        this.user_id=user_id;
        this.tokenType=tokenType;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) 
            return true;
    
        if (o == null || getClass() != o.getClass())
            return false;
    
        SecureTokenId that = (SecureTokenId) o;
        return Objects.equals(tokenType, that.tokenType) &&
                    Objects.equals(user_id, that.user_id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(tokenType, user_id);
    }
    
    public void setTokenType(String tokenType)
    {
      this.tokenType=tokenType;
    }
    
    public String getTokenType()
    {
      return this.tokenType;
    }
    
    public void setTokenId(Long id)
    {
      this.user_id=id;
    }
    
    public Long getTokenId()
    {
      return this.user_id;
    }
    
        
}
