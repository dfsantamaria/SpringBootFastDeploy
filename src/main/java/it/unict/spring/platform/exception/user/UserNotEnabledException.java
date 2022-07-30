package it.unict.spring.platform.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import org.springframework.dao.DataAccessException;

public class UserNotEnabledException extends DataAccessException
{
    public UserNotEnabledException(String string)
    {
        super("Registration not completed: "+ string);
    }    
}
