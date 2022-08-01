package com.ggshin.oauthdemo.controller;

import com.ggshin.oauthdemo.auth.PrincipalDetails;
import com.ggshin.oauthdemo.model.Member;
import com.ggshin.oauthdemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public @ResponseBody String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/join")
    public String join() {
        return "joinForm";
    }

    @GetMapping("/loginTest")
    @ResponseBody
    public String loginTest(Authentication authentication) {
        System.out.println("==================/loginTest==================");
        //loginForm 로그인을 한 경우에는 authentication.getPrincipal가 PrincipalDetials type이다.
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication: " + principalDetails.getMember());

        return "세션 정보 확인";
    }

    @GetMapping("/loginTest2")
    @ResponseBody
    public String loginTest2(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("==================/loginTest2==================");
        System.out.println("userDetails(password): " + principalDetails.getPassword());
        System.out.println("userDetails(username): " + principalDetails.getUsername());
        System.out.println("userDetails(authorities): " + principalDetails.getAuthorities());
        System.out.println("userDetails(member): " + principalDetails.getMember());


        return "세션 정보 확인2";
    }

    @GetMapping("/loginTest3")
    @ResponseBody
    public String loginOAuthTest(Authentication authentication,
                                 @AuthenticationPrincipal OAuth2User oAuth) {
        System.out.println("==================/loginOAuthTest==================");
        //OAuth2 로그인을 한 경우에는 authentication.getPrincipal가 OAuth2User type이다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication: " + oAuth2User.getAttributes());
        System.out.println("oauth2User: " + oAuth.getAttributes());

        return "세션 정보 확인3";
    }

    @PostMapping("/join")
    public String join(Member member) {
        member.setRole("ROLE_USER");
        String rawPassword = member.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(encPassword);

        memberRepository.save(member);
        return "redirect:/login";
    }
}






























