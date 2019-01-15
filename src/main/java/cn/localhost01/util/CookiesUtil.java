package cn.localhost01.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: cookie操作类
 * @Author Ran.chunlin
 * @Date: Created in 15:13 2019-01-13
 */
public abstract class CookiesUtil {

    /**
     * 根据名字获取cookie
     *
     * @param request 请求
     * @param name    名字
     * @return cookie
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request 请求
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 保存Cookies
     *
     * @param response 响应
     * @param name     名字
     * @param value    值
     * @param time     存活时间/秒
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time)
            throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        URLEncoder.encode(value, "utf-8");
        cookie.setMaxAge(time);
        //防止xss
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return response;
    }
}
