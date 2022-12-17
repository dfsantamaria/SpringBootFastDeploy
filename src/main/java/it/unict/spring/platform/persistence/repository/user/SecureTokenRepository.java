package it.unict.spring.platform.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> 
{    
 @Override
 List<SecureToken> findAll();  
 List<SecureToken> findAllByToken(String token); 
 List<SecureToken> findAllByUser(UserAccount user);
 Optional<SecureToken> findOneByToken(String token);
 Optional<SecureToken> findOneByUser(UserAccount user);
 Optional<SecureToken> findOneByUser_IdAndTokenId_TokenType(Long id, String token);
 @Override 
 SecureToken save(SecureToken user);   
 @Override
 void delete(SecureToken user);  
    
}
