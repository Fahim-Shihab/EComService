package net.javaguides.springboot.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class LoggedInUser implements Serializable, UserDetails {
    private String userId;
    private String email;
    private String password;
    private String userType;
    private String userStatus;
    private Date entryDate;
    private Date updDate;
    private Collection<? extends GrantedAuthority> authorities;

    public LoggedInUser(UserInfo userInfoEO) {
        if(userInfoEO != null) {
            this.userId = userInfoEO.getUserInfoId().getUserId();
            this.email = userInfoEO.getUserInfoId().getUserEmail();
            this.password = userInfoEO.getPassword();
            this.userType = userInfoEO.getUserType();
            this.userStatus = userInfoEO.getUserStatus();
            this.entryDate = userInfoEO.getEntryDate();
            this.updDate = userInfoEO.getUpdDate();
            this.authorities = new ArrayList<>();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
