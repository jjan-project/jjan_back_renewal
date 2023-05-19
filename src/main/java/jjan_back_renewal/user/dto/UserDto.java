package jjan_back_renewal.user.dto;

import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String nickName;
    private String password;
    private String profile;
    private String name;
    private String address;
    private String gender;
    private String birth;

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.nickName = userEntity.getNickName();
        this.password = userEntity.getPassword();
        this.profile = userEntity.getProfile();
        this.name = userEntity.getName();
        this.address = userEntity.getAddress();
        this.gender = userEntity.getGender();
        this.birth = userEntity.getBirth();

    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .email(email)
                .nickName(nickName)
                .password(password)
                .profile(profile)
                .name(name)
                .address(address)
                .gender(gender)
                .birth(birth)
                .build();
    }
}

