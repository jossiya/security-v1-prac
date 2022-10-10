package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//스프링 시큐리티 필터()가 스프링 필어체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class SecurityConfig {
    
    @Bean//해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF protection 을 비활성화
        http.csrf().disable();

        http
                .authorizeHttpRequests((authz) -> authz
                        // image 폴더를 login 없이 허용
                        .antMatchers("/images/**").permitAll()
                        // css 폴더를 login 없이 허용
                        .antMatchers("/css/**").permitAll()
                        // 회원 관리 처리 API 전부를 login 없이 허용
//                        .antMatchers("/user/**").permitAll()
                        //기본 인증이 필요하다는 뜻
                                .antMatchers("/user/**").authenticated()
                        .antMatchers("/manager/**").hasAnyRole("ROLE_MANAGER","ROLE_ADMIN")
                        .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
                        //.antMatchers("/manager/**").hasRole("ROLE_ADMIN or ROLE_MANAGER")
                        // 어떤 요청이든 '인증'
//                        .anyRequest().authenticated()
                        //페이지 인증 필요 없음
                                .anyRequest().permitAll()
                )
                 //[로그인 기능]
                .formLogin()
                // 로그인 View 제공 (GET /user/login)
                .loginPage("/user/login")
//                //username 객체 바꾸고 싶을 때 사용
////                .usernameParameter("username2123123123123123")
//                // 로그인 처리 (POST /user/login)
                .loginProcessingUrl("/user/login")//login 주소가 호출이 되면 시큐리티가 낚아채면서 대신 로그인을 진행해줌
////                // 로그인 처리 후 성공 시 URL
                .defaultSuccessUrl("/")
                // 로그인 처리 후 실패 시 URL
                .failureUrl("/user/login?error")
                .permitAll()
                .and()
                // [로그아웃 기능]
                .logout()
                // 로그아웃 처리 URL
                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                // "접근 불가" 페이지 URL 설정
                .accessDeniedPage("/forbidden.html");

        return http.build();
    }
}
