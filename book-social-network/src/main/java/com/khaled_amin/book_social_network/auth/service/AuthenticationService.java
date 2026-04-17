package com.khaled_amin.book_social_network.auth.service;

import com.khaled_amin.book_social_network.auth.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.common.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.service.RoleService;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public void register(@Valid RegistrationRequest request) {

    }

}
