package it.unict.spring.platform.service.user;

import it.unict.spring.platform.persistence.model.user.UserLogin;
import it.unict.spring.platform.persistence.repository.user.UserLoginRepository;
import it.unict.spring.platform.serviceinterface.user.UserLoginServiceInterface;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

@Service
public class UserLoginService implements UserLoginServiceInterface
{
    @Autowired
    UserLoginRepository repository;
    
    @Override
    @Transactional
    public UserLogin save (UserLogin userlogin)
    {
      return repository.save(userlogin);
    }
    
    @Override
    public List<UserLogin> findAll()
    {
        return (List<UserLogin>) repository.findAll();
    }
        
    @Override
    public Optional<UserLogin> findById(Long id)
    {
      return repository.findById(id);
    }
    
    @Override
    @Transactional
    public void delete(UserLogin userlogin)
    {
      repository.delete(userlogin);
    }
    
    @Override
    @Transactional
    public void resetLoginFail(UserLogin userlogin)
    {
       userlogin.setFailCount(0);
       userlogin.setLastFailDate(null);
       this.save(userlogin);
    }
    
    @Override        
    public void updateLoginFail(UserLogin userlogin)
    {       
       int count=userlogin.getFailCount()+1;
       userlogin.setFailCount(count);
       userlogin.setLastFailDate(Timestamp.valueOf(LocalDateTime.now()));
       this.save(userlogin);
    }
}
