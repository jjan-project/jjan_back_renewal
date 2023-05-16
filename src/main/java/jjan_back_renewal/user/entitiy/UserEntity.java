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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String birth;

    //프로필 이미지
    private String profile;

    @Builder
    public UserEntity(Long id, String email, String password, String nickName, String address, String sex, String birth, String profile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
        this.sex = sex;
        this.birth = birth;
        this.profile = profile;
    }
}

