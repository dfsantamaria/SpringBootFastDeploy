package it.unict.spring.application.serviceinterface.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.persistence.model.user.SecureToken;
import it.unict.spring.application.persistence.model.user.UserAccount;
import java.util.List;


public interface SecureTokenServiceInterface
{
    List<SecureToken> findAll();
    SecureToken generateToken (String tokenType);
    SecureToken save (SecureToken token);  
    void delete(SecureToken token);  
    void addUserToToken(UserAccount user, SecureToken token);
    List<SecureToken> findByUser(UserAccount user);
    
}
