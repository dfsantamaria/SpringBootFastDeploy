package it.unict.spring.platform.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

public class UserAccountAlreadyVerified extends Exception
{
  public UserAccountAlreadyVerified(String message)
  {
    super("Account already verified with token: "+ message);
  }
}
