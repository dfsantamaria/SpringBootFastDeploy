package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.dto.user.UserRegisterDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.persistence.repository.user.UserRegisterRepository;
import it.unict.spring.platform.serviceinterface.user.UserRegisterServiceInterface;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserRegisterService implements UserRegisterServiceInterface
{
    @Autowired
    UserRegisterRepository repository;
      
    @Override
    public List<UserRegister> findAll()
    {
        return (List<UserRegister>) repository.findAll();
    }
    
    @Override
    @Transactional
    public UserRegister save (UserRegister g)
    {
      return repository.save(g);
    }
    
        
    @Override
    @Transactional
    public void delete(UserRegister register)
    {
      repository.delete(register);
    }
              
    @Override
    public UserRegister mapFromUserRegister(UserRegisterDTO dto)
    {
      return new UserRegister(dto.getFirstName(), dto.getMiddleName(), dto.getLastName());
    }

    public void setUser(UserRegister reg, UserAccount user)
    {
      reg.setUser(user);
    }
}
