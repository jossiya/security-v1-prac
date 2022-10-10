package com.cos.security1.auth;

//시큐리티가 /login 주소요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인을 진행이 완료가 되면 시큐리티 session을 만들어줍니다.(Security ContextHolder)
//세션에 들어갈 수 있는 오브젝트가 정해져 있는데 Authentication 타입 객체
//Authentication 안에 User 정보가 있어야됨
//User 오브젝트 타입=> UserDetail 타입 객체여야함

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//Security Session 에 저장할 타입은 Authentication =>저장 될 타입은 UserDetail 타입
public class PrincipalDetail implements UserDetails {

    private final User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    //해당 User의 권한을 리턴하는 곳 !!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Collection<? extends GrantedAuthority> 타입으로 변환하는 과정
        String userRole = user.getRole();

        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(userRole);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        //니계정 만료됬니? true 값이 아니오라는 뜻
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //니계정 잠겼니?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //비밀번호 너무 오래 사용한거 아니니 -->휴면 계정 로직
        return true;
    }

    @Override
    public boolean isEnabled() {
        //니 계정이 활성화 되있니.
        return true;
    }
}
