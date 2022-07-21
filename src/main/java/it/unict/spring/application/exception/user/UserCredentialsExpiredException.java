package it.unict.spring.application.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import org.springframework.dao.DataAccessException;

public class UserCredentialsExpiredException extends DataAccessException
{
    public UserCredentialsExpiredException(String string)
    {
        super("User credentials are expired: "+ string);
    }       
}
