package it.unict.spring.platform.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.persistence.model.user.UserRegister;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> 
{
    @Override
    List<UserRegister> findAll();
    @Override 
    UserRegister save(UserRegister register);   
    @Override
    void delete(UserRegister register); 
}
