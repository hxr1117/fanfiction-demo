package com.example.ffdemo.service;

import com.example.ffdemo.dto.UserDto;
import com.example.ffdemo.model.User;

public interface UserService {
    User saveUser(UserDto userDto);
    User getUserByEmail(String email);
    String getUsernameById(String userId);
    User getUserById(String id);

}
