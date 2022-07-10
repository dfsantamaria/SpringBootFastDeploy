package it.unict.spring.application.utility.user;

import it.unict.spring.application.persistence.model.user.Privilege;
import it.unict.spring.application.persistence.model.user.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class  CustomUserDetails implements UserDetails 
{
    private final Users user;
    public CustomUserDetails(Users user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for ( Privilege privilege : user.getPrivileges()) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
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
    