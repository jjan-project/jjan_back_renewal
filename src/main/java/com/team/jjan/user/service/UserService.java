package com.team.jjan.user.service;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.join.service.JoinService;
import com.team.jjan.jwt.support.JwtProvider;
import com.team.jjan.upload.service.FileUploadService;
import com.team.jjan.user.dto.AddressRequest;
import com.team.jjan.user.dto.JoinResponse;
import com.team.jjan.user.dto.LocateRequest;
import com.team.jjan.user.dto.RequestData;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;

import static com.team.jjan.common.ResponseCode.REQUEST_SUCCESS;
import static com.team.jjan.join.service.JoinService.createUUIDString;
import static com.team.jjan.jwt.support.JwtCookie.deleteJwtTokenInCookie;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JoinService joinService;
    private final FileUploadService fileUploadService;

    @Transactional
    public ResponseMessage setNickName(CurrentUser currentUser, RequestData RequestData) throws AccountException {
        String userEmail = currentUser.getEmail();
        String nickName = RequestData.getData();

        if (!joinService.isNickNameLengthOK(nickName)) {
            throw new AccountException("유효하지 않은 닉네임입니다.");
        }
        if (!joinService.checkDuplicatedNickName(nickName)) {
            throw new AccountException("사용중인 닉네임입니다.");
        }
        if (isReplaceableUser(userEmail)) {
            throw new AccountException("닉네임은 한 번만 변경할 수 있습니다.");
        }

        setNickName(userEmail ,nickName);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    @Transactional
    public ResponseMessage setDrinkCapacity(CurrentUser currentUser, RequestData RequestData) {
        String userEmail = currentUser.getEmail();
        String capacity = RequestData.getData();

        setDrinkCapacity(userEmail,capacity);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    @Transactional
    public ResponseMessage setProfileImage(CurrentUser currentUser, MultipartFile multipartFile) throws IOException {
        String userEmail = currentUser.getEmail();

        setProfileImage(userEmail , multipartFile);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    @Transactional
    public ResponseMessage deleteUser(CurrentUser currentUser , HttpServletResponse response) {
        String userEmail = currentUser.getEmail();

        deleteUserByEmail(userEmail);
        deleteJwtTokenInCookie(response);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    @Transactional
    public ResponseMessage deleteUserByEmail(String userEmail) {
        userRepository.deleteByEmail(userEmail);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    @Transactional
    public void setProfileImage(String email , MultipartFile multipartFile) throws IOException {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다."));

        fileUploadService.deleteFile(targetUser.getProfile());

        String imageUrl = fileUploadService.uploadFileToS3(multipartFile , createUUIDString());
        targetUser.setProfile(imageUrl);
    }

    public void setNickName(String email, String nickName) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다."));

        targetUser.setNickName(nickName);
        targetUser.setNickNameChangeAvailable(true);
    }

    public void setDrinkCapacity(String email, String capacity) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다."));

        targetUser.setDrinkCapacity(capacity);
    }

    public boolean isReplaceableUser(String email) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다."));

        return targetUser.isNickNameChangeAvailable();
    }


    public JoinResponse getUserInfo(CurrentUser currentUser) {
        UserEntity targetUser = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다."));

        return new JoinResponse(targetUser);
    }

    @Transactional
    public ResponseMessage setAddress(CurrentUser currentUser, AddressRequest addressRequest) {
        UserEntity targetUser = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다."));

        targetUser.updateAddress(addressRequest);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    @Transactional
    public ResponseMessage modifyLocate(CurrentUser currentUser, LocateRequest locateRequest) {
        UserEntity targetUser = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다."));

        targetUser.updateLocate(locateRequest);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }
}
