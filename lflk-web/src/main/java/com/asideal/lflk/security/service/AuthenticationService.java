package com.asideal.lflk.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    Authentication getAuthentication();
}
