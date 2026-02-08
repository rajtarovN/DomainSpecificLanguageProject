package uns.ac.rs.mbrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.InheritanceType.JOINED;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "system_user")
@Inheritance(strategy=JOINED)
public abstract class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "username", unique=true, nullable=false)
    private String username;

    @Column(name = "password", unique=false, nullable=false)
    private String password;

    @Column(name = "deleted", unique=false, nullable=false)
    private boolean deleted;
    @Column(name = "loggedFirstTime", unique=false, nullable=false)
    private boolean loggedFirstTime;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new Authority("ROLE_USER"));
        authorities.add(new Authority("ROLE_" + this.role.getName()));
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}