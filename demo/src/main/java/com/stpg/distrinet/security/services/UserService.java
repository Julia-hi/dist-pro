package com.stpg.distrinet.security.services;

import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.models.DocumentPinned;
import com.stpg.distrinet.models.User;
import com.stpg.distrinet.repository.UserRepository;
import com.stpg.distrinet.services.DocumentPinnedService;
import com.stpg.distrinet.services.DocumentService;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentService documentService;

    public User getUser(Long id) {
        Optional<User> uo = userRepository.findById(id);
        if (uo.isPresent()) {
            return uo.get();
        } else {
            throw new ServiceException(String.format("User with id = %d does not exist", id));
        }
    }

    public User changeTheirInfo(Long id, String name, String title, String phoneNumber){
        User user = getUser(id);
        user.setName(name);
        user.setTitle(title);

        userRepository.save(user);

        return user;
    }



}
