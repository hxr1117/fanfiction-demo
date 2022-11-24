package com.example.ffdemo.securityweb;

import com.example.ffdemo.model.User;
import com.example.ffdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("3");

        System.out.println(userRepository);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("登录用户[" + username + "]没注册!");
        }
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return Arrays.asList(Collections.emptyList());
    }
}
