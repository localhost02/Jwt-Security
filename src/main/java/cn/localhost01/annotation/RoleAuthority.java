package cn.localhost01.annotation;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public final class RoleAuthority implements GrantedAuthority {
    @Getter
    private final String role;

    @Getter
    private final String authorities;

    public RoleAuthority(String role, String authorities) {
        this.role = role;
        this.authorities = authorities;
    }

    public String getAuthority() {
        return this.authorities;
    }

    public boolean equals(Object obj) {
        return this == obj ?
                true :
                (obj instanceof RoleAuthority ?
                        (this.role.equals(((RoleAuthority) obj).role) && this.authorities
                                .equals(((RoleAuthority) obj).authorities)) :
                        false);
    }

    public int hashCode() {
        return this.role.hashCode() + this.authorities.hashCode();
    }

    public String toString() {
        return "RoleAuthority [" + this.role + "," + this.authorities + "]";
    }
}
