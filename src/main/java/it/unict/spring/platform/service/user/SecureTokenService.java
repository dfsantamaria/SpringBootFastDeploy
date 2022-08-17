package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.SecureTokenId;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.repository.user.SecureTokenRepository;
import it.unict.spring.platform.serviceinterface.user.SecureTokenServiceInterface;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecureTokenService implements SecureTokenServiceInterface
{
    
    private static final Logger applogger = LoggerFactory.getLogger(SecureTokenService.class);  
 
    
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
      return repository.findAllByUser(user);
    }
       
    
    @Override
    public Optional<SecureToken> findByToken(String token)
    {
     return repository.findOneByToken(token);
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
    public SecureToken generateToken(UserAccount user, String tokenType)
    {
      byte[] random = new byte[64];
      new SecureRandom().nextBytes(random);
      Timestamp timestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
      LocalDateTime expire= LocalDateTime.now().plusHours(12);
      SecureToken token = new SecureToken(new SecureTokenId(user, tokenType),Base64.encodeBase64URLSafeString(random),                                          
                                          timestamp, Timestamp.valueOf(expire));      
      
      return token;
    }

    
    @Override
    @Transactional
    public void consumeToken(SecureToken sec)
    {
      sec.setIsConsumed(Timestamp.valueOf(LocalDateTime.now()));
      applogger.info("Token consumed: "+ sec.getToken() + "user id:"+ sec.getTokenId().getTokenId());
    }
           
    
}
