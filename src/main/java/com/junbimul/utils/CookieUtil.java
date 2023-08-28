package com.junbimul.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie makeRefreshToken(String refreshToken) {
        Cookie cookie = new Cookie("REFRESH_TOKEN", refreshToken);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 10); // 10분
        cookie.setHttpOnly(true);
        return cookie;
    }
}
