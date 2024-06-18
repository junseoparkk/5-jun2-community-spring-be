package com.kcs.community.service.user;

import com.kcs.community.dto.user.SignupRequest;
import com.kcs.community.dto.user.SignupResponse;
import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    SignupResponse signup(SignupRequest request) throws IllegalArgumentException;
    UserInfoDto findByEmail(String email);
    UserInfoDto updateInfo(UserInfoDto userDto, String nickname, MultipartFile profileImg);
}
