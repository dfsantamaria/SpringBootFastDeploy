package it.unict.spring.platform.utility.communication;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import javax.mail.MessagingException;
import javax.mail.Store;


public class JavaMailReader
{
    private Store store;
    private String username;
    private String password;
    private String host;
    
    public JavaMailReader(Store store, String host, String username, String password)
    {
      this.host=host;  
      this.store=store;
      this.username=username;
      this.password=password;
    }

    public void connect() throws MessagingException
    {
       store.connect(host, username, password);       
    }

    public Store getStore()
    {
      return store;
    }   
    
}
