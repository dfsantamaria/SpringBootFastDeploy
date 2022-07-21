package it.unict.spring.platform.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import org.springframework.dao.DataAccessException;

public class UserAccountLockedException extends DataAccessException
{
    public UserAccountLockedException(String string)
    {
        super("User account is locked: "+ string);
    }    
}