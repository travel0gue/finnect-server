package com.example.finnect.service;

import com.example.finnect.apiResponse.ErrorStatus;
import com.example.finnect.exception.CustomException;
import com.example.finnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername "+ username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_FOUND));
    }
}