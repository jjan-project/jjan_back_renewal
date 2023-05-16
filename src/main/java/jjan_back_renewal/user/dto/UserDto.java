package jjan_back_renewal.user.dto;

import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String address;
    private String sex;
    private String birth;
    private String profile;

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.nickName = userEntity.getNickName();
        this.address = userEntity.getAddress();
        this.sex = userEntity.getSex();
        this.birth = userEntity.getBirth();
        this.profile = userEntity.getProfile();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickName(nickName)
                .address(address)
                .sex(sex)
                .birth(birth)
                .profile(profile)
                .build();
    }
}

