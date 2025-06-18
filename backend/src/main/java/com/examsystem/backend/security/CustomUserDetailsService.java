package com.examsystem.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.examsystem.backend.entity.User;
import com.examsystem.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with uid: " + uid);
        }
        return new CustomUserDetails(user);
    }
}