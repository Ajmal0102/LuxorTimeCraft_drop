package com.ajmal.LuxorTimeCraft.service;

import com.ajmal.LuxorTimeCraft.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    List<User> findAllUsers();
    Optional<User> findUserByEmail(String email);

    void save(User user);

    Optional<User> findById(Integer id);

    void delete(User user1);

    void otpManagement(User user);

    int verifyAccount(String email, String otp);
}
