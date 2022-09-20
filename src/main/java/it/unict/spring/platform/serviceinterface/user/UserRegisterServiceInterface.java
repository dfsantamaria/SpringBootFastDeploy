package it.unict.spring.platform.serviceinterface.user;

import it.unict.spring.platform.dto.user.UserRegisterDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


public interface UserRegisterServiceInterface
{
    List<UserRegister> findAll();
    Optional<UserRegister> findById(Long id);
    Optional<UserRegister> findByUser(UserAccount user);
    Page<UserRegister> findByFirstnameOrMiddlenameOrLastname(String first, String middle, String last, Pageable pageable);
    List<UserRegister> findAll(Example<UserRegister> example);
    Page<UserRegister> findAll(Example<UserRegister> example, Pageable pageable);
    UserRegister save (UserRegister g);    
    void delete(UserRegister register);     
    UserRegister mapFromUserRegister(UserRegisterDTO dto);
    void setUser(UserRegister reg, UserAccount user);
}
