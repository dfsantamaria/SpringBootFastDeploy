package it.unict.spring.platform.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
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
    
       
    public Long getTokenId()
    {
      return this.user.getId();
    }
    
        
}
