package com.ajmal.LuxorTimeCraft.service;

import com.ajmal.LuxorTimeCraft.model.User;
import com.ajmal.LuxorTimeCraft.otp.model.Otp;
import com.ajmal.LuxorTimeCraft.otp.repository.OtpRepository;
import com.ajmal.LuxorTimeCraft.otp.service.SendEmail;
import com.ajmal.LuxorTimeCraft.otp.utils.OtpUtil;
import com.ajmal.LuxorTimeCraft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    OtpUtil otpUtil;
    @Autowired
    SendEmail sendEmail;
    @Autowired
    OtpRepository otpRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void otpManagement(User user) {

        String otp= otpUtil.generateOtp();
        sendEmail.send(user.getEmail(),otp);

        Otp otp1=new Otp();

        otp1.setOtp(otp);
        otp1.setTime(LocalDateTime.now());
        otp1.setUser(user);

        otpRepository.save(otp1);

    }

    @Override
    public int verifyAccount(String email, String incomingOtp) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Otp otp = otpRepository.findByUser(user);
            if(otp.getOtp().equals(incomingOtp)){
                if( Duration.between(otp.getTime(),LocalDateTime.now()).getSeconds()<=300){
                    user.setVerified(true);
                    userRepository.save(user);
                    otpRepository.delete(otp);
                    return 1;
                }else {
                    return 2;
                }
            }
            return 0;
        }
        return 9;

    }

}
