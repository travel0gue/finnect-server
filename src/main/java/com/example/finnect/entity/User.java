package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(nullable = false)
    private boolean isEnabled;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Farmer farmer;

    public void setFarmer(Farmer farmer) {
        // 기존 관계가 있다면 제거
        if (this.farmer != null) {
            this.farmer.setUser(null);
        }

        this.farmer = farmer;

        // 새로운 관계 설정
        if (farmer != null) {
            farmer.setUser(this);
        }
    }


    // 인증 관련 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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