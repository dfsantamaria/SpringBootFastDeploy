package it.unict.spring.application.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.dto.user.UserRegisterDTO;
import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.persistence.model.user.UserRegister;
import it.unict.spring.application.persistence.repository.user.UserRegisterRepository;
import it.unict.spring.application.serviceinterface.user.UserRegisterServiceInterface;
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
    public void addUserToRegister(UserAccount user, UserRegister register)
    {
       register.setUser(user);
       this.save(register);
    }
    
    @Override
    @Transactional
    public void delete(UserRegister register)
    {
      repository.delete(register);
    }
    
    @Override
    @Transactional
    public UserRegister addUserToRegister(UserAccount user, String firstname, String middlename, String lastname)
    {
       UserRegister reg = new UserRegister(firstname, middlename, lastname);
       reg.setUser(user);
       this.save(reg);
       return reg;
    }

    @Override
    @Transactional
    public void setUser(UserRegister register, UserAccount user)
    {
         register.setUser(user);           
         //this.save(register);       
    }
    
    @Override
    public UserRegister mapFromUserRegister(UserRegisterDTO dto)
    {
      return new UserRegister(dto.getFirstName(), dto.getMiddleName(), dto.getLastName());
    }
}
