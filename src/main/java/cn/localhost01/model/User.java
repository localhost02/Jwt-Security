package cn.localhost01.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Description:实体对象
 * @Author Ran.chunlin
 * @Date: Created in 14:36 2019-01-13
 */
public class User implements UserDetails {

    @Getter @Setter private String username;

    @Getter @Setter private String password;

    @Setter private List<? extends GrantedAuthority> authorities;

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }
}
