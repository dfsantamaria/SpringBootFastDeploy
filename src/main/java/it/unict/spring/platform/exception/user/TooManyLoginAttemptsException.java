package it.unict.spring.platform.exception.user;

import org.springframework.dao.DataAccessException;


/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


public class TooManyLoginAttemptsException extends DataAccessException
{
    public TooManyLoginAttemptsException(String string)
    {
        super("Too many login attempts from " + string);
    }    
}
