package br.com.gabxdev.company.domain;


import br.com.gabxdev.core.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_account_user")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountUser extends Auditable implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole()
                .stream()
                .map(role -> {
                    role = "ROLE_" + role;
                    return new SimpleGrantedAuthority(role);
                })
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    public List<String> getRole() {
        return Arrays.stream(role.split(",")).toList();
    }

    public void setRole(String role) {
        this.role = String.join(",", role);
    }
}
