package com.javatechie.springsecurityjwtexample.sécurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorization extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Headers",
//                "Origin , Accept, X-Requested-With, Content-Type, " +
//                        "Access-Control-Request-Method, " +
//                        "Access-Control-Request-Headers," +
//                        "Authorization");
//        response.addHeader("Access-Control-Expose-Headers",
//                "Access-Control-Allow-Origin," +
//                        "Access-Control-Allow-Credentials," +
//                        "Authorization");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String jwt = request.getHeader(SecurityConstants.HEADER_STRING);
            if (jwt != null) {
                if (!jwt.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody();

            String username = claims.getSubject();
            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
            });

            UsernamePasswordAuthenticationToken authenticationUser = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationUser);
            filterChain.doFilter(request, response);
        }
    }

}
