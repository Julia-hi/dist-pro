package com.stpg.distrinet.security.services;

import com.stpg.distrinet.models.DocumentPinned;
import com.stpg.distrinet.models.User;
import com.stpg.distrinet.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void changeMyPassword(String oldPassword, String newPassword) {

    }

}
