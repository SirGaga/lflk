package com.asideal.lflk.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 授权
 * @author ZhangJie
 */
@Service
public interface AuthenticationService {
    /**
     * 获取授权
     * @return 返回授权实体
     */
    Authentication getAuthentication();
}
