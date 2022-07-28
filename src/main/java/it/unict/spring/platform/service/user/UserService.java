package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.dto.user.UserAccountDTO;
import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.exception.user.UserAccountAlreadyVerified;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.serviceinterface.user.UserServiceInterface;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.persistence.repository.user.UserRepository;
import it.unict.spring.platform.service.communication.CustomMailService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserServiceInterface
{
    @Autowired
    UserRepository repository;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    SecureTokenService secureTokenService;
    @Autowired
    UserRegisterService registryService;
    @Autowired
    CustomMailService mailService;
    
    @Autowired
    PasswordEncoder getPasswordEncoder;
           
    @Override
    public List<UserAccount> findAll()
    {
        return (List<UserAccount>) repository.findAll();
    }
    
    @Override
    public List<UserAccount> findByUsername(String name)
    {
      return repository.findByUsername(name);
    }
    
    @Override
    public List<UserAccount> findByMail(String email)
    {
      return repository.findByMail(email);
    }
    
    @Override 
    public List<UserAccount> findByMailOrUsername(String namemail)
    {
       return repository.findAllByMailOrUsername(namemail, namemail);
    }
  
    @Override 
    public List<UserAccount> findByMailOrUsername(String namemail, String username)
    {
       return repository.findAllByMailOrUsername(namemail, username);
    }
    
    public UserRegister findByCustomUserDetail(CustomUserDetails userdetails)
    {
        String mail =userdetails.getMail();
        List<UserAccount> users= this.findByMail(mail);
       
        if(!(users.isEmpty()))
        {            
            Optional<UserRegister> r = registryService.findByUser(users.get(0));
            return (r.isPresent()?r.get():null);
        }
        return null;
    }
    
    @Override
    @Transactional
    public void delete(UserAccount user)
    {
      repository.delete(user);
    }
    
    @Override
    @Transactional
    public void deleteUser(UserAccount user)
    {
      List<UserAccount> list = this.findByMail(user.getMail());
      if(!list.isEmpty())
          this.delete(list.get(0));   
    }
    
    @Override   
    @Transactional
    public UserAccount save (UserAccount g)
    {
      return repository.save(g);
    }
    
    @Override
    public String encodePassword(String password)
    {
      return getPasswordEncoder.encode(password);
    }
    
    @Override
    public boolean comparePassword(String hash, String plain)
    {
      return getPasswordEncoder.matches(plain, hash);
    }
    
    /*
    * Enable the give user
    */
    @Override
    @Transactional
    public void setEnabled(UserAccount user, boolean enabled)
    {      
      user.setEnabled(enabled);      
    }
    
    @Override
    @Transactional
    public UserAccount findById(Long id)
    {
      return repository.findById(id).get();
    }
    
    @Override
    @Transactional
    public UserAccount getSuperAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getSuperAdminPrivilege(); 
      return this.getUser(username, password, mail, accountExpire, credentialExpire, organization, priv);       
    }
    
    /*
    * Create or retrive a user assigning the given organization and privilege
    */
    @Override
    @Transactional
    public UserAccount getUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire,
                               String organization, Privilege priv) throws MultipleUsersFoundException
    {         
        List<UserAccount> users = repository.findAllByMailOrUsername(mail, username);            
        UserAccount user=null;
        if(users.size()> 1)
            throw new MultipleUsersFoundException("Combination of username and password gets multiple users: "+ username + ", "+mail);
        else if(users.size()==1)
             user = users.get(0);
        else if(users.isEmpty())        
           { 
             user=new UserAccount(username, this.encodePassword(password), mail, accountExpire, credentialExpire);                            
           }  
           if (user==null)
               return null;
            user.addPrivileges(priv);
            privilegeService.save(priv);
            Organization org = organizationService.getOrSetOrganization(organization);            
            user.addOrganization(org);
            organizationService.save(org);           
        return user;       
    }
    
    @Override
    @Transactional
    public void addRegisterToUser(UserRegister register, UserAccount account)
    {
      account.setRegister(register);      
    }
    
    
    @Override
    @Transactional
    public UserAccount getStandardUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      
      Privilege priv= privilegeService.getStandardUserPrivilege(); 
      return this.getUser(username, password, mail, accountExpire,  credentialExpire, organization, priv);       
    }
    
      

    @Override
    @Transactional
    public UserAccount mapFromUserDTO(UserAccountDTO userdto, Timestamp accountExpire, Timestamp credentialExpire, UserRegister register, Organization organization) throws MultipleUsersFoundException
    {    
      if(!(this.findByMailOrUsername(userdto.getMail(), userdto.getUsername())).isEmpty() )  
          throw new MultipleUsersFoundException("Account already exists");
      UserAccount user= this.getStandardUser(userdto.getUsername(),
                        userdto.getPassword().getPassword(),
                        userdto.getMail(), accountExpire, credentialExpire,
                        organization.getName());      
      this.save(user);
      return user;
    }
    

    @Override   
    @Transactional
    public void sendRecoverPasswordMail(UserAccount user, String url)
    {       
      this.sendEmail(user, "Reset your password", url, "/checkResetPassword?token=", "RPass");  
    }
    
    @Override   
    @Transactional
    public void sendRegistrationMail(UserAccount user, String url)
    {       
      this.sendEmail(user, "Confirm registration", url,"/registrationConfirm?token=", "FReg");  
    }
    
    @Transactional
    private void sendEmail(UserAccount user, String body,  String url, String prefix, String type)
    {
       
      SecureToken token=this.assignTokenToUser(user, type);     
      user=this.save(user);
      token=secureTokenService.save(token);
      mailService.sendSimpleEmail(user.getMail(), body, url+prefix + token.getToken());
    }
    
    @Override
    @Transactional
    public SecureToken assignTokenToUser(UserAccount user, String type)
    {         
      for(SecureToken token : secureTokenService.findByUser(user))                       
         secureTokenService.delete(token);      
      SecureToken token = secureTokenService.generateToken(user, type); 
      //user.addSecureToken(token); Error
      return token;
    }  
    
    
              
    @Override
    @Transactional    
    public boolean verifyRegistrationToken(String token) throws UserAccountAlreadyVerified
    {
        
      List<SecureToken> sec= secureTokenService.findByToken(token);
      if(sec.isEmpty())
          return false;
      if(sec.get(0).getExpireAt().after(Timestamp.valueOf(LocalDateTime.now())))
      {
         UserAccount user=this.findById(sec.get(0).getId().getTokenId());
         if(!user.isEnabled() && !sec.get(0).isConsumed())
         {
             secureTokenService.consumeToken(sec.get(0));
             secureTokenService.save(sec.get(0));             
             user.setEnabled(true);             
             this.save(user);
             return true;
         }
         else throw new UserAccountAlreadyVerified(sec.get(0).getToken());
      }
      return false;
    }
    
    @Override
    @Transactional    
    public boolean verifyPasswordChangedToken(String token, String password)
    {        
      List<SecureToken> sec= secureTokenService.findByToken(token);
      if(sec.isEmpty())
          return false;
      if(!sec.get(0).isConsumed() && sec.get(0).getTokenType().equals("RPass") && sec.get(0).getExpireAt().after(Timestamp.valueOf(LocalDateTime.now())))
      {
         UserAccount user=this.findById(sec.get(0).getId().getTokenId());
         secureTokenService.consumeToken(sec.get(0));
         secureTokenService.save(sec.get(0)); 
         user.setPassword(this.encodePassword(password));
         this.save(user);
         return true;
       }      
      return false;
    }
    
    /* 
      
 
    
    
    
    @Override
    @Transactional
    public boolean hasUserPrivilege(String privilege)
    {
     Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
     SecurityContextHolder.getContext().getAuthentication().getAuthorities();
     boolean hasRole = false;
     for (GrantedAuthority authority : authorities)
     {
      hasRole = authority.getAuthority().equals(privilege);
      if (hasRole)
       break;     
     }
      return hasRole;      
  } 
    
    @Override
    public String getUserByEmail(String mail) throws UserNotFoundException
    {
        List<UserAccount> users = repository.findByMail(mail);
        if(users.isEmpty())        
              throw new UserNotFoundException("user not found using email "+ mail); 
        else
            return users.get(0).getMail();
    }

    @Override 
    @Transactional
    public UserAccount getAdminUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getAdminPrivilege(); 
      return this.getUser(username, password, mail, accountExpire,  credentialExpire, organization, priv);
    }
    
    @Override
    @Transactional
    public UserAccount getUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire,
                               String organization, Privilege priv) throws MultipleUsersFoundException
    {         
        List<UserAccount> users = repository.findByMail(mail);    
        users.addAll(repository.findByUsername(username));
        UserAccount user=null;
        if(users.size()>1)
            throw new MultipleUsersFoundException("Combination of username and password gets multiple users: "+ username + ", "+mail);
        else if(users.size()==1)
             user=users.get(0);
        else if(users.isEmpty())        
           {              
            Organization org = organizationService.getOrSetOrganization(organization); 
            org=organizationService.save(org);
            user=new UserAccount(username, this.encodePassword(password), mail, accountExpire, credentialExpire);                            
            this.addOrganizationToUser(org,user); //user.addOrganization(org);            
            //organizationService.addUserToOrganization(user, org);  //Error          
            organizationService.save(org);
            this.addPrivilegeToUser(priv, user); //user.addPrivileges(priv);  
            //privilegeService.addUserToPrivilege(user, priv);
            privilegeService.save(priv);
            this.save(user);                    
           } 
        return user;       
    }

    @Override
    @Transactional
    public UserAccount getStaffUser(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire, String organization) throws MultipleUsersFoundException
    {
      Privilege priv= privilegeService.getStaffPrivilege(); 
      return this.getUser(username, password, mail, accountExpire, credentialExpire, organization, priv);    
    }
            
    
  */
}