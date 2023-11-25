package com.ajmal.LuxorTimeCraft.otp.repository;

import com.ajmal.LuxorTimeCraft.model.User;
import com.ajmal.LuxorTimeCraft.otp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp,Long> {


    Otp findByUser(User user);
}
