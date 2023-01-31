package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final UserMapper userMapper;
}
