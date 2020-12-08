package com.javatechie.springsecurityjwtexample.services;

import com.javatechie.springsecurityjwtexample.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class userDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser user = userService.findUserByEmail(s);
        if (user == null) throw new UsernameNotFoundException(s);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
