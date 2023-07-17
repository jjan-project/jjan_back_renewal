package com.team.jjan.join.service;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.join.dto.*;
import com.team.jjan.jwt.support.JwtProvider;
import com.team.jjan.upload.dto.FileUploadResponse;
import com.team.jjan.upload.service.FileUploadService;
import com.team.jjan.user.dto.JoinResponse;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.UUID;

import static com.team.jjan.common.ResponseCode.REQUEST_SUCCESS;
import static com.team.jjan.jwt.support.JwtCookie.setCookieFromJwt;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JavaMailSender mailSender;
    private final FileUploadService fileUploadService;

    public JoinResponse login(LoginRequest loginRequest , HttpServletResponse response) throws AccountException {
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("Wrong Authentication");
        }
        setCookieFromJwt(response, jwtProvider.createToken(userEntity.getEmail(), userEntity.getRoles()));

        return new JoinResponse(userEntity);
    }

    @Transactional
    public ResponseMessage join(JoinRequest joinRequest , MultipartFile profileImage) throws IOException {
        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        UserEntity userEntity = UserEntity.createUserEntity(joinRequest , encodedPassword);

        UserEntity result = userRepository.save(userEntity);
        uploadProfileImage(result , profileImage);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    public void uploadProfileImage(UserEntity userEntity , MultipartFile profileImage) throws IOException {
        String uuid = createUUIDString();
        String imageUrl = "blank";

        if (profileImage != null) {
            imageUrl = fileUploadService.uploadFileToS3(profileImage , uuid);
        }

        userEntity.setProfile(imageUrl);
    }

    public PasswordResponseDto resetPassword(PasswordRequestDto passwordRequestDto) {
        UserEntity userEntity = userRepository.findByEmail(passwordRequestDto.getEmail())
                .orElseThrow(() -> new NoSuchEmailException(passwordRequestDto.getEmail()));

        if (passwordRequestDto.getNickname().equals(userEntity.getNickName())) {
            MailDto mail = createMailAndChangePassword(userEntity.getEmail(), userEntity.getNickName());
            mailSend(mail);
        }
        else {
            return new PasswordResponseDto(null);
        }

        return new PasswordResponseDto(passwordRequestDto.getEmail());
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

    public String createUUIDString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Transactional
    public void updatePassword(String email, String tempPassword){
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        targetUser.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(targetUser);
    }

    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}
