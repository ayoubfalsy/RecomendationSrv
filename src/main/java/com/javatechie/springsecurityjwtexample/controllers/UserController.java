package com.javatechie.springsecurityjwtexample.controllers;

import com.javatechie.springsecurityjwtexample.entities.AppRole;
import com.javatechie.springsecurityjwtexample.entities.AppUser;
import com.javatechie.springsecurityjwtexample.entitiesStatus.Inc;
import com.javatechie.springsecurityjwtexample.entitiesStatus.Pwd;
import com.javatechie.springsecurityjwtexample.services.SendEmailService;
import com.javatechie.springsecurityjwtexample.services.UserService;
import com.javatechie.springsecurityjwtexample.services.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.logging.Level;

@RequestMapping(value = "/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SendEmailService sendEmailService;

    @PostMapping("/inscription")
    public ResponseEntity<Long> register(@RequestBody Inc clsInc, HttpServletRequest request) {
        Long lngExist = 0l;
        AppUser user = new AppUser();
        String strRemoteAddr = "";
        String strGeneratePwd = "";
        String strEmailFrom = "portfoliosupp@gmail.com";
        long intTimeCreate = System.currentTimeMillis();
        String strText = "Voila votre mot de passe : ";
        String subject = "Nouveau Mot de passe";
        Random r = new java.util.Random();
        try {
            AppUser appUser = userService.findUserByEmail(clsInc.getEmail());
            if (appUser != null) {
                lngExist = 1l;
            } else {
                if (request != null) {
                    strRemoteAddr = request.getHeader("X-FORWARDED-FOR");
                    if (strRemoteAddr == null || "".equals(strRemoteAddr))
                        strRemoteAddr = request.getRemoteAddr();
                }
                strGeneratePwd = Long.toString(r.nextLong() & Long.MAX_VALUE, 36);
                user.setAddIp(strRemoteAddr);
                user.setTimeCreate(intTimeCreate);
                user.setPassword(strGeneratePwd);
                user.setEmail(clsInc.getEmail());
                user.setPays(clsInc.getCountry());
                user.setSexe(clsInc.getSexe());
                userService.SaveUser(user);
                userService.SaveRole(new AppRole("USER"));
                userService.addRoleToUser(clsInc.getEmail(), "USER");
                sendEmailService.sendEmail(clsInc.getEmail(), strEmailFrom, subject, strText + strGeneratePwd);

            }
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);

        }
        return new ResponseEntity<Long>(lngExist, HttpStatus.OK);

    }

    @GetMapping("/getUser/{strEmail}")
    public AppUser getUserByEmail(@PathVariable String strEmail) {
        AppUser clsAppUser = null;
        clsAppUser = userService.findUserByEmail(strEmail);
        return clsAppUser;
    }

    @PostMapping("/checkPwd")
    public ResponseEntity<?> ChangePwd(@RequestBody Pwd pwd) {
        Pwd clsPwd1 = new Pwd();
        AppUser clsAppUser = null;
        boolean isPasswordMatches;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            clsAppUser = userService.findUserByEmail(pwd.getEmail());
            isPasswordMatches = passwordEncoder.matches(pwd.getOldPwd(), clsAppUser.getPassword());
            if (isPasswordMatches) {
                clsAppUser.setPassword(pwd.getNewPwd());
                userService.SaveUser(clsAppUser);
                // password Changes
                pwd.setType(1);
            } else {
                // password incorrect
                pwd.setType(2);
            }
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Pwd>(pwd, HttpStatus.OK);
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<?> ChangeEmail(@RequestBody Pwd pwd) {
        AppUser clsPwd1 = new AppUser();
        AppUser clsAppUser = null;
        boolean isPasswordMatches;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            clsAppUser = userService.findUserByEmail(pwd.getEmail());
            clsPwd1 = userService.findUserByEmail(pwd.getNewPwd());
            isPasswordMatches = passwordEncoder.matches(pwd.getOldPwd(), clsAppUser.getPassword());
//            if (clsPwd1 == null) {
            if (isPasswordMatches) {
                clsAppUser.setEmail(pwd.getNewPwd());
                clsAppUser.setPassword(pwd.getOldPwd());
                clsAppUser.setSexe(pwd.getConfirmPwd());
                userService.SaveUser(clsAppUser);
                // password Changes
                pwd.setType(1);
//                } else {
                // password incorrect
//                    pwd.setType(2);
//                }
            } else {
                pwd.setType(3);

            }
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Pwd>(pwd, HttpStatus.OK);
    }

    @PostMapping("/forgot/{strEmail}")
    public ResponseEntity<?> Forgot(@PathVariable String strEmail) {
        AppUser clsAppUser = null;
        Boolean blnType = false;
        String strText = "";
        Random r = new java.util.Random();
        String strPwd;
        String subject;
        try {
            clsAppUser = userService.findUserByEmail(strEmail);
            if (clsAppUser != null) {
                strPwd = Long.toString(r.nextLong() & Long.MAX_VALUE, 36);
                strText = "Voila votre mot de passe : " + strPwd;
                subject = "Nouveau Mot de passe";
                clsAppUser.setPassword(strPwd);
                userService.SaveUser(clsAppUser);
                sendEmailService.sendEmail(strEmail, strEmail, subject, strText);
                blnType = true;
            }

        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Boolean>(blnType, HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> LogOut(@RequestBody String strEmail) {
        Boolean blnType = false;
        try {
            blnType = true;
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Boolean>(blnType, HttpStatus.OK);

    }
}
