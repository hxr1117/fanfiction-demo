package com.example.ffdemo.service;

import com.example.ffdemo.dto.UserDto;
import com.example.ffdemo.model.User;
import com.example.ffdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getEmail(),
                userDto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        return user;
    }

    @Override
    public String getUsernameById(String userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get().getUsername();
        }
        return null;
    }
}
