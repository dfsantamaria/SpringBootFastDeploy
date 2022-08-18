package it.unict.spring.platform.utility.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.unict.spring.platform.persistence.model.user.Privilege;
import it.unict.spring.platform.persistence.model.user.UserAccount;


public class  CustomUserDetails implements UserDetails 
{
    private static final long serialVersionUID = 4953362963386345424L;
    private final UserAccount user;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(UserAccount user)
    {
        this.user = user;
        this.authorities=getAuthorities(user);
    }
    
    private List<GrantedAuthority> getAuthorities(UserAccount user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<Privilege> privileges = user.getPrivileges();
//        Hibernate.initialize(privileges);
        for (Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        return authorities;
    }

    public UserAccount getUser()
    {
      return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }
    
    @Override
    public String getPassword()
    {
       return user.getPassword();
    }

    @Override
    public String getUsername()
    {
      return user.getUsername();
    }
    
    public String getMail()
    {
      return user.getMail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
       return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled()
    {
       return user.isEnabled();
    }
    
    
}
    