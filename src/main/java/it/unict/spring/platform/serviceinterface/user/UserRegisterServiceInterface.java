package it.unict.spring.platform.serviceinterface.user;

import it.unict.spring.platform.dto.user.UserRegisterDTO;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import java.util.List;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

public interface UserRegisterServiceInterface
{
    List<UserRegister> findAll();
    UserRegister save (UserRegister g);    
    void delete(UserRegister register);     
    UserRegister mapFromUserRegister(UserRegisterDTO dto);
}
