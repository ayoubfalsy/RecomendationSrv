package com.javatechie.springsecurityjwtexample.sécurityConfig;

public interface SecurityConstants {
    public static final String SECRET = "med@youssfi.net";
    public static final long EXPIRATION_TIME = 864_000_000; // 10jrs;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
