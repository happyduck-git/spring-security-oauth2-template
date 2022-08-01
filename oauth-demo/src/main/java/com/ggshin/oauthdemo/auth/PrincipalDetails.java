package com.ggshin.oauthdemo.auth;

import com.ggshin.oauthdemo.model.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//Member entity를 Security와 맞물리게 하는 부분
@Data
public class PrincipalDetails implements UserDetails, OAuth2User { //PrincipalDetails에 UserDetails와 OAuth2User를 implements하여 일반 로그인과 OAuth2 로그인이 별도로 처리되어 발생하는 문제 해결

    private Member member;
    private Map<String, Object> attributes; //OAuth2 회원가입 기능 구현 위해 추가

    //일반 로그인
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    //OAuth 로그인
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }


    @Override
    public Map<String, Object> getAttributes() {
//        return null; //null 리턴에서 attributes return으로 수정
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
