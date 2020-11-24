package com.asideal.lflk.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

    /**
     *  对字符串加密
     * @param password 加密对象字符串
     * @return 已加密结果字符串
     */
    public static String QuickPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
    
    public static boolean passwordMatch (String passwordFont,String passwordDB){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(passwordFont,passwordDB);
    }

    public static void main(String[] args) {
        String s = "/system";

        System.out.println(s.substring(1).substring(0,1).toUpperCase()+s.substring(1).substring(1));
    }

}
