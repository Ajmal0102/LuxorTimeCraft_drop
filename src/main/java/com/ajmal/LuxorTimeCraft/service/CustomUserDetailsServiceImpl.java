package com.ajmal.LuxorTimeCraft.service;

import com.ajmal.LuxorTimeCraft.model.CustomUserDetail;
import com.ajmal.LuxorTimeCraft.model.User;
import com.ajmal.LuxorTimeCraft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()){
          User  user1 = user.get();
          if(!user1.isActive()){
              throw new RuntimeException("User is disabled by the admin!");
          }

        }
        user.orElseThrow(()-> new UsernameNotFoundException("User not found."));
        return user.map(CustomUserDetail::new).get();
    }
}
