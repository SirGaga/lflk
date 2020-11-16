package com.asideal.lflk.system.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacAuthorityService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
