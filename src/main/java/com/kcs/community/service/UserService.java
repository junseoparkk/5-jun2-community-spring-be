package com.kcs.community.service;

import com.kcs.community.auth.JwtToken;
import com.kcs.community.dto.user.LoginRequest;
import com.kcs.community.dto.user.SignupRequest;
import com.kcs.community.dto.user.SignupResponse;

public interface UserService {
    SignupResponse signup(SignupRequest request) throws IllegalArgumentException;
    JwtToken login(LoginRequest request);
}
