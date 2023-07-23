package com.stpg.distrinet.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import com.fasterxml.jackson.annotation.JsonView;
import com.stpg.distrinet.models.ennum.ERole;
import com.stpg.distrinet.models.DocumentPinned;
import com.stpg.distrinet.models.Role;
import com.stpg.distrinet.models.User;
import com.stpg.distrinet.payload.request.ChangePasswordRequest;
import com.stpg.distrinet.payload.request.ChangeVariousRequest;
import com.stpg.distrinet.security.services.AuthService;
import com.stpg.distrinet.security.services.UserDetailsImpl;
import com.stpg.distrinet.payload.response.JwtResponse;
import com.stpg.distrinet.payload.response.MessageResponse;
import com.stpg.distrinet.repository.UserRepository;
import com.stpg.distrinet.security.jwt.JwtUtils;
import com.stpg.distrinet.payload.request.LoginRequest;
import com.stpg.distrinet.payload.request.SignupRequest;
import com.stpg.distrinet.repository.DocumentPinnedRepository;
import com.stpg.distrinet.repository.RoleRepository;
import com.stpg.distrinet.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    @Transactional
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        if (loginRequest.getToken() != null) {
            User user = userRepository.getById(userDetails.getId());
            user.setToken(loginRequest.getToken());
            user.setDateToken(loginRequest.getDataToken());
        }
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = null;
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
        roles.add(userRole);


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@Validated  @RequestBody  ChangePasswordRequest changePasswordRequest, Authentication current){

        // Details of the current logged user.
        UserDetailsImpl currentPrincipal = (UserDetailsImpl) current.getPrincipal();
        // If the old password passed through as a parameter equals the old password, proceed
        String oldEncoded = encoder.encode(changePasswordRequest.getOldPassword());
        if (encoder.matches(changePasswordRequest.getOldPassword(), currentPrincipal.getPassword())){
            // Proceed changing the password
            User user = userRepository.getById(((UserDetailsImpl) current.getPrincipal()).getId());
            String newEncoded = encoder.encode(changePasswordRequest.getNewPassword());
            user.setPassword(newEncoded);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User password changed successfully!"));

        }
        else{ // otherwise return error
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Incorrect password"));
        }
    }




}
