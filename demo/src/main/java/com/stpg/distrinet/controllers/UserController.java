package com.stpg.distrinet.controllers;

import com.stpg.distrinet.models.User;
import com.stpg.distrinet.payload.request.ChangeVariousRequest;
import com.stpg.distrinet.repository.UserRepository;
import com.stpg.distrinet.security.services.UserDetailsImpl;
import com.stpg.distrinet.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;


    @GetMapping(path="/{id}")
    public User getUser(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping(path="/myData")
    public User getMyData(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userService.getUser(userDetails.getId());
    }




    @PostMapping("/change/various")
    public User changeVarious(@RequestBody ChangeVariousRequest changeVariousRequest, Authentication current){
        UserDetailsImpl currentPrincipal = (UserDetailsImpl) current.getPrincipal();
        User changed = userService.changeTheirInfo(currentPrincipal.getId(), changeVariousRequest.getName(), changeVariousRequest.getTitle(),  changeVariousRequest.getPhoneNumber());
        return changed;
    }

}