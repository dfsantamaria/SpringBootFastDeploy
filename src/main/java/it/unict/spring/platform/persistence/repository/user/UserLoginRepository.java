package it.unict.spring.platform.persistence.repository.user;

import it.unict.spring.platform.persistence.model.user.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> 
{
    @Override 
    UserLogin save(UserLogin register);   
    @Override
    void delete(UserLogin register);     
    @Override
    Optional<UserLogin> findById(Long id);
}
