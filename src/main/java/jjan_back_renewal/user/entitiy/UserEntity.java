package jjan_back_renewal.user.entitiy;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jjan_back_renewal.party.entity.PartyEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String birth;

    @Column(nullable = false)
    private String drinkCapacity;

    @Column(nullable = false)
    private Double location_x;

    @Column(nullable = false)
    private Double location_y;

    @Column
    private boolean isNickNameChangeAvailable = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<PartyEntity> parties = new ArrayList<>();

    @Builder
    public UserEntity(Long id, String email, String nickName, String password, String profile, String address, String gender, String birth, String drinkCapacity, Double location_x, Double location_y, boolean isNickNameChangeAvailable, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.profile = profile;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
        this.drinkCapacity = drinkCapacity;
        this.roles = roles;
        this.location_x = location_x;
        this.location_y = location_y;
        this.isNickNameChangeAvailable = isNickNameChangeAvailable;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}

