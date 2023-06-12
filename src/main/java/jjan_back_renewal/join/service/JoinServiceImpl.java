package jjan_back_renewal.join.service;

import jjan_back_renewal.join.dto.*;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.join.auth.JwtProvider;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JoinServiceImpl implements JoinService {

    private JavaMailSender mailSender;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        UserEntity userEntity = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Wrong Authentication"));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("Wrong Authentication");
        }
        return new LoginResponseDto(new UserDto(userEntity), jwtProvider.createToken(userEntity.getEmail(), userEntity.getRoles()));
    }

    @Override
    public JoinResponseDto join(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = userRepository.save(userDto.toEntity());
        return new JoinResponseDto(new UserDto(userEntity), jwtProvider.createToken(userEntity.getEmail(), userEntity.getRoles()));
    }

    @Override
    public PasswordResponseDto resetPassword(PasswordRequestDto passwordRequestDto) {
        UserEntity userEntity = userRepository.findByEmail(passwordRequestDto.getEmail())
                .orElseThrow(() -> new NoSuchEmailException(passwordRequestDto.getEmail()));

        if (passwordRequestDto.getName() == userEntity.getName()) {
            MailDto mail = createMailAndChangePassword(userEntity.getEmail(), userEntity.getName());
            mailSend(mail);
            return new PasswordResponseDto(passwordRequestDto.getEmail());
        }
        else {
            return new PasswordResponseDto(null);
        }
    }

    public MailDto createMailAndChangePassword(String email, String name) {
        String tempPassword = getTempPassword();
        MailDto mailDto = new MailDto();
        mailDto.setAddress(email);
        mailDto.setTitle(name + "님의 임시비밀번호 안내 이메일 입니다.");
        mailDto.setMessage("안녕하세요. 임시비밀번호 안내 관련 이메일 입니다." + "[" + name + "]" + "님의 임시 비밀번호는 "
                + tempPassword + " 입니다.");
        updatePassword(email, tempPassword);
        return mailDto;
    }

    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    @Transactional
    public void updatePassword(String email, String tempPassword){
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        targetUser.setPassword(passwordEncoder.encode(tempPassword));
    }

    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}
