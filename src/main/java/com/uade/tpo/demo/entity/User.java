package com.uade.tpo.demo.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data // @Data hace que se generen los getters y setters automaticamente
@Builder
@AllArgsConstructor
@Entity
public class User implements UserDetails{
    public User() {
        // Constructor por defecto requerido por JPA
    }

    // public User(String userName, String email, String password) {
    //     this.userName = userName;
    //     this.email = email;
    //     this.password = password;
    // }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Critic> critics = new ArrayList<>();

    // @JoinColumn:
    // Especifica que esta relación se mapeará con una clave foránea en la tabla User.
    
    // nullable = false:
    // Asegura que esta columna no permita valores null, es decir, cada usuario debe tener un rol asignado.

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnabled() {
        return true;
    }

    //Si o si se tienen que llamar así
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUsername() {
        return email;
    }
}
