package com.kcs.community.service;

import com.kcs.community.dto.user.SignupRequest;
import com.kcs.community.dto.user.SignupResponse;
import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.User;

public interface UserService {
    SignupResponse signup(SignupRequest request) throws IllegalArgumentException;
    UserInfoDto findByEmail(String email);
}
