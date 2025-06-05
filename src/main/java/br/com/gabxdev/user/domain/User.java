package br.com.gabxdev.user.domain;


import br.com.gabxdev.core.audit.Auditable;
import br.com.gabxdev.core.common.Role;
import br.com.gabxdev.core.common.UserIdentity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends Auditable implements UserDetails, UserIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID accountId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole()
                .stream()
                .map(role -> {
                    var authority = "ROLE_" + role.toString();
                    return new SimpleGrantedAuthority(authority);
                })
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    public List<Role> getRole() {
        return Arrays
                .stream(role.split(","))
                .map(Role::valueOf)
                .toList();
    }

    public void setRole(String role) {
        this.role = String.join(",", role);
    }
}
