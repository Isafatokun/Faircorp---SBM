//package com.emse.spring.faircorp.api;
//
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@Controller
//public class SecurityController {
//
//    @Secured("ROLE_ADMIN") // (1)
//    @GetMapping(path = "/{id}")
//    public String findUserName(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String id) {
//        return userDetails.getUsername();
//    }
//}