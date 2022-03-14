package net.javaguides.springboot.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class UserInfoId implements Serializable {

    @Column(name="user_id")
    private String userId;

    @Column(name="user_email")
    private String userEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoId)) return false;
        UserInfoId that = (UserInfoId) o;
        return Objects.equals(getUserEmail(), that.getUserEmail()) &&
                Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserEmail(), getUserId());
    }
}
