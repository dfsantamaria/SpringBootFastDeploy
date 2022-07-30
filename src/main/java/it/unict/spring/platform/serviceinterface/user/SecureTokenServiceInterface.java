package it.unict.spring.platform.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import java.util.List;


public interface SecureTokenServiceInterface
{
    List<SecureToken> findAll();
    SecureToken generateToken (UserAccount user, String tokenType);
    SecureToken save (SecureToken token);  
    void delete(SecureToken token);      
    
    List<SecureToken> findByUser(UserAccount user);
    List<SecureToken> findByToken(String token);
    void consumeToken(SecureToken sec);
    
}
