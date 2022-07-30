package it.unict.spring.platform.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import org.springframework.dao.DataAccessException;

public class UserAccountExpiredException extends DataAccessException
{
    public UserAccountExpiredException(String string)
    {
        super("User account is expired: "+ string);
    }    
}
