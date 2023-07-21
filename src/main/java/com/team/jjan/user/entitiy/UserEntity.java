package com.team.jjan.user.entitiy;

import com.team.jjan.join.dto.JoinRequest;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyJoin;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //생성한 파티
    @OneToMany(mappedBy = "author")
    private List<PartyEntity> createParties = new ArrayList<>();

    //가입한 파티
    @OneToMany(mappedBy = "joinUser")
    private List<PartyJoin> partyJoins = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Date birth;

    @Column(nullable = false)
    private String drinkCapacity;

    @Column
    private boolean isNickNameChangeAvailable = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roles;

    public static UserEntity createUserEntity(JoinRequest joinRequest , String encodedPassword) {
        return UserEntity.builder()
                .email(joinRequest.getEmail())
                .password(encodedPassword)
                .address(joinRequest.getAddress())
                .birth(joinRequest.getBirth())
                .gender(joinRequest.getGender())
                .profile("blank")
                .nickName(joinRequest.getNickname())
                .drinkCapacity(joinRequest.getDrinkingCapacity())
                .roles(Role.MEMBER)
                .build();
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(roles.getRole()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

