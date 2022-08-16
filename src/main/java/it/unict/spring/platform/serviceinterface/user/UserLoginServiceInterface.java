package it.unict.spring.platform.serviceinterface.user;

import it.unict.spring.platform.persistence.model.user.UserLogin;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

public interface UserLoginServiceInterface
{
    UserLogin save (UserLogin userlogin);    
    void delete(UserLogin userlogin); 
    List<UserLogin> findAll();   
    public Optional<UserLogin> findById(Long id);
    public void updateLoginFail(UserLogin userlogin);
    public void resetLoginFail(UserLogin userlogin);
}
