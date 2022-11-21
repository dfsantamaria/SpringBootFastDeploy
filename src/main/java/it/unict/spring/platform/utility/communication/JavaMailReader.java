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
    private String inbox;
    
    public JavaMailReader(Store store, String host, String username, String password, String inbox)
    {
      this.host=host;  
      this.store=store;
      this.username=username;
      this.password=password;
      this.inbox=inbox;
    }

    public void connect() throws MessagingException
    {
       store.connect(host, username, password);       
    }

    public Store getStore()
    {
      return store;
    }   
    
    public String getInboxFolderName()
    {
        return this.inbox;
    }
    
}
