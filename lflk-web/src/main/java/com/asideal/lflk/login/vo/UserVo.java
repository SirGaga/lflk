package com.asideal.lflk.login.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserVo {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

}
