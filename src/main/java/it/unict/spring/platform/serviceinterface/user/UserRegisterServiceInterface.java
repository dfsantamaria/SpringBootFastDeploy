package it.unict.spring.platform.serviceinterface.user;

import it.unict.spring.platform.dto.user.UserRegisterDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

public interface UserRegisterServiceInterface
{
    List<UserRegister> findAll();
    Optional<UserRegister> findById(Long id);
    Optional<UserRegister> findByUser(UserAccount user);
    UserRegister save (UserRegister g);    
    void delete(UserRegister register);     
    UserRegister mapFromUserRegister(UserRegisterDTO dto);
}
