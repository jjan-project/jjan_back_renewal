package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    //client에게 dto로 정보를 입력 받고
    //Entity에 Setter 없이 Mapper를 통해 DTO로 client -> Controller -> Entity로 값을 넘긴다
    @Override
    public UserEntity register(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        //. . . . .

        return userEntity;
    }

    //need Exceptions
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //need Exceptions
    @Override
    public UserEntity findByNickName(String nickName) {
        return userRepository.findByNickName(nickName);
    }

}
