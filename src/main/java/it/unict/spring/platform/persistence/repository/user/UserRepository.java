package it.unict.spring.platform.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.persistence.model.user.UserAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> 
{
    List<UserAccount> findAllByUsername(String username);
    List<UserAccount> findAllByMail(String email);    
    List<UserAccount> findAllById(Long id);
    @Override
    List<UserAccount> findAll();
    List<UserAccount> findAllByMailOrUsername(String mail, String username);
    @Override 
    UserAccount save(UserAccount user);   
    @Override
    void delete(UserAccount user); 
    @Override
    void deleteById(Long id);  
}