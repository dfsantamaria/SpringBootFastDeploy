package it.unict.spring.application.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.persistence.model.user.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> 
{
    List<Users> findByUsername(String username);
    List<Users> findByMail(String email);
    @Override
    List<Users> findAll();
    @Override 
    Users save(Users user);   
    @Override
    void delete(Users user);    
    
}