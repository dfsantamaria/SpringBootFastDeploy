package it.unict.spring.platform.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> 
{
    Optional<UserAccount> findOneByUsername(String username);
    Optional<UserAccount> findOneByMail(String email);    
    Optional<UserAccount> findOneById(Long id);    
    @Override
    List<UserAccount> findAll();    
    List<UserAccount> findAllByMailOrUsername(String mail, String username);
    Page<UserAccount> findAllByMailOrUsername(String mail, String username, Pageable pageable);
    @Override 
    UserAccount save(UserAccount user);   
    @Override
    void delete(UserAccount user); 
    @Override
    void deleteById(Long id);  
}