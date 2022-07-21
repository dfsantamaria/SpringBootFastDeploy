package it.unict.spring.application.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import org.springframework.dao.DataAccessException;

public class UserAccountExpiredException extends DataAccessException
{
    public UserAccountExpiredException(String string)
    {
        super("User account is expired: "+ string);
    }    
}
