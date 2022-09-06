package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.dto.user.UserRegisterDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.persistence.repository.user.UserRegisterRepository;
import it.unict.spring.platform.serviceinterface.user.UserRegisterServiceInterface;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserRegisterService implements UserRegisterServiceInterface
{
    @Autowired
    UserRegisterRepository repository;
      
    @Override
    @Transactional
    public List<UserRegister> findAll()
    {
        return (List<UserRegister>) repository.findAll();
    }
    
    @Override
    @Transactional
    public Page<UserRegister> findByFirstnameOrMiddlenameOrLastname(String first, String middle, String last, Pageable pageable)
    {
      return repository.findByFirstnameOrMiddlenameOrLastname(first, middle, last, pageable);  
    }
    
    @Override
    @Transactional
    public Optional<UserRegister> findByUser(UserAccount user)
    {
      return repository.findById(user.getId());
    }
    
    @Override
    @Transactional
    public Optional<UserRegister> findById(Long id)
    {
      return repository.findById(id);
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
     
    /*
    * Map from the give DTO to UserRegister
    */
    @Override
    @Transactional
    public UserRegister mapFromUserRegister(UserRegisterDTO dto)
    {
      return new UserRegister(dto.getFirstName(), dto.getMiddleName(), dto.getLastName());
    }

    @Override
    @Transactional
    public void setUser(UserRegister reg, UserAccount user)
    {
      reg.setUser(user);
    }

}
