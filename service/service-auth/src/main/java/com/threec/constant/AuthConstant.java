package com.threec.constant;

/**
 * 验证常数
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:55
 */
public class AuthConstant {
    public static final String[] API_AUTH = {"api/auth/register","api/auth/authenticate","api/auth/getSmsCaptcha"};
    public static final String JWT_EXPIRED = "TC JWT expired";
    public static final String JWT_UNSUPPORTED = "TC Unsupported JWT";
    public static final String JWT_MALFORMED = "TC Malformed JWT";
    public static final String JWT_INVALID_SIGNATURE = "TC Invalid signature";
    public static final String JWT_ILLEGAL_PARAM = "TC Illegal param";
    public static final String BEARER = "Bearer ";
    public static final String ERROR_USERNAME_OR_PASSWORD = "TC username or password error";
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHENTICATION_FAILED = "TC Authentication failed, please login again";
    public static final String ACCESS_DENIED = "TC access denied";
    public static final String LOGOUT_SUCCESS = "TC Logout success";
    public static final String[] WHITE_LIST_URL = {"/api/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html"};
    public static final String AUTH_LOGOUT = "/auth/logout";


}
