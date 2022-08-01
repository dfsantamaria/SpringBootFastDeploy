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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> 
{
    @Override
    List<UserRegister> findAll();   
    List<UserRegister> findAllById(Long id);
    List<UserRegister> findAllByUser(UserAccount user);
    @Override 
    UserRegister save(UserRegister register);   
    @Override
    void delete(UserRegister register); 
}
