package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.exception.UserNotFoundException;
import com.emrhnsyts.leaveamark.repository.AppUserRepository;
import com.emrhnsyts.leaveamark.request.AppUserRequest;
import com.emrhnsyts.leaveamark.response.AppUserCreateResponse;
import com.emrhnsyts.leaveamark.response.AppUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserCreateResponse addUser(AppUserRequest appUserRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserRequest.getUsername());
        appUser.setEmail(appUserRequest.getEmail());
        // to be encoded
        appUser.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        appUserRepository.save(appUser);
        return new AppUserCreateResponse(appUser);
    }

    public List<AppUserResponse> getUsers() {
        return appUserRepository.findAll().stream().map(appUser -> {
            return new AppUserResponse(appUser);
        }).collect(Collectors.toList());
    }

    public AppUserResponse getUser(Long userId) {
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        if (!appUser.isPresent()) {
            throw new UserNotFoundException(String.format("User with %s id number not found.", userId));
        }
        return new AppUserResponse(appUser.get());
    }

    public void deleteUser(Long userId) {
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        if (!appUser.isPresent()) {
            throw new UserNotFoundException(String.format("User with %s id number not found.", userId));
        }
        appUserRepository.delete(appUser.get());
    }

    protected List<AppUser> getUsersForService() {
        return appUserRepository.findAll();
    }

    protected AppUser getUserForService(Long userId) {
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        if (!appUser.isPresent()) {
            throw new UserNotFoundException(String.format("User with %s id number not found.", userId));
        }
        return appUser.get();
    }

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if (!optionalAppUser.isPresent()) {
            throw new UsernameNotFoundException("Username not found.");
        }
        return optionalAppUser.get();
    }
}
