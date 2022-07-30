package it.unict.spring.platform.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import org.springframework.dao.DataAccessException;

public class UserCredentialsExpiredException extends DataAccessException
{
    public UserCredentialsExpiredException(String string)
    {
        super("User credentials are expired: "+ string);
    }       
}
