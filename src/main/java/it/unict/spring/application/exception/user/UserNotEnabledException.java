package it.unict.spring.application.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import org.springframework.dao.DataAccessException;

public class UserNotEnabledException extends DataAccessException
{
    public UserNotEnabledException(String string)
    {
        super("User is not enabled: "+ string);
    }    
}
