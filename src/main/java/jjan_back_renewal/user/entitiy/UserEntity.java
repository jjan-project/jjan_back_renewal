package jjan_back_renewal.user.entitiy;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String birth;

    @Builder
    public UserEntity(Long id, String email, String nickName, String password, String profile, String name, String address, String gender, String birth) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.profile = profile;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
    }





}

