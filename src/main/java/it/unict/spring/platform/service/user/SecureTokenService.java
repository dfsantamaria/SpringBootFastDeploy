package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.repository.user.SecureTokenRepository;
import it.unict.spring.platform.serviceinterface.user.SecureTokenServiceInterface;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author danie
 */
@Service
public class SecureTokenService implements SecureTokenServiceInterface
{
    @Autowired
    SecureTokenRepository repository;
    
    @Override    
    public List<SecureToken> findAll()
    {
      return repository.findAll();
    }
    
    @Override   
    public List<SecureToken> findByUser(UserAccount user)
    {
      return repository.findByUser(user);
    }


    @Override
    @Transactional
    public SecureToken save(SecureToken token)
    {
      return repository.save(token);
    }

    @Override
    @Transactional
    public void delete(SecureToken token)
    {
        repository.delete(token);
    }
    
    
    @Override
    @Transactional
    public void addUserToToken(UserAccount user, SecureToken token)
    {
      token.addUser(user);
      this.save(token);
    }
    
    @Override
    @Transactional
    public SecureToken generateToken(String tokenType)
    {
      byte[] random = new byte[64];
      new SecureRandom().nextBytes(random);
      Timestamp timestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
      LocalDateTime expire= LocalDateTime.now().plusHours(12);
      SecureToken token = new SecureToken(Base64.encodeBase64URLSafeString(random),
                                          tokenType,
                                          timestamp, expire);                                             
      
      return token;
    }
    
    
}
