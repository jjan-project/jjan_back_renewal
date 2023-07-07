package jjan_back_renewal.user.dto;

import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.entitiy.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String nickName;
    private String password;
    private String profile;
    private String address;
    private String gender;
    private String birth;
    private String drinkCapacity;
    private Double location_x;
    private Double location_y;
    private boolean isNickNameChangeAvailable = true;
    private List<Role> roles = new ArrayList<>();

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.nickName = userEntity.getNickName();
        this.password = userEntity.getPassword();
        this.profile = userEntity.getProfile();
        this.address = userEntity.getAddress();
        this.gender = userEntity.getGender();
        this.birth = userEntity.getBirth();
        this.drinkCapacity = userEntity.getDrinkCapacity();
        this.roles = userEntity.getRoles();
        this.location_x = userEntity.getLocation_x();
        this.location_y = userEntity.getLocation_y();
        this.isNickNameChangeAvailable = userEntity.isNickNameChangeAvailable();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .email(email)
                .nickName(nickName)
                .password(password)
                .profile(profile)
                .address(address)
                .gender(gender)
                .birth(birth)
                .drinkCapacity(drinkCapacity)
                .roles(roles)
                .location_x(location_x)
                .location_y(location_y)
                .isNickNameChangeAvailable(isNickNameChangeAvailable)
                .build();
    }
}

