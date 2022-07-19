package it.unict.spring.application.serviceinterface.user;

import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.persistence.model.user.UserRegister;
import java.util.List;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */
public interface UserRegisterServiceInterface
{
    List<UserRegister> findAll();
    UserRegister save (UserRegister g);
    void addUserToRegister(UserAccount user, UserRegister register);
    void delete(UserRegister register);
    UserRegister addUserToRegister(UserAccount user, String firstname, String middlename, String lastname);
    void setUser(UserRegister register, UserAccount user);
}
