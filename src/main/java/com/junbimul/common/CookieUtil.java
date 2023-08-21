package com.junbimul.common;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

public class CookieUtil {

    public static HttpServletResponse setAccessToken(HttpServletResponse response, String token) {

        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(1000 * 30);
        response.addCookie(cookie);
        return response;
    }

    public static HttpServletResponse setRefreshToken(HttpServletResponse response, String token) {

        Cookie cookie = new Cookie("refresh_token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(1000 * 60 * 60 * 24);
        response.addCookie(cookie);
        return response;
    }
}
