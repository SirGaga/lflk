package com.asideal.lflk.test.generator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

    public static String QuickPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static void main(String[] args) {
        System.out.println(QuickPassword("111111"));
    }
}
