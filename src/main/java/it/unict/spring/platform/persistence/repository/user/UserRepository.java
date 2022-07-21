package it.unict.spring.platform.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.persistence.model.user.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> 
{
    List<UserAccount> findByUsername(String username);
    List<UserAccount> findByMail(String email);    
    @Override
    List<UserAccount> findAll();
    @Override 
    UserAccount save(UserAccount user);   
    @Override
    void delete(UserAccount user); 
    @Override
    void deleteById(Long id);  
}