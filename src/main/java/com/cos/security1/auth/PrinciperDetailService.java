package com.cos.security1.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/user/login")
// /user/login 요청이 오면 자동으로 UserDetailService 타입으로 ioc가 되어 있는 loadUserByUsername 함수가 실행됨
@Service
@RequiredArgsConstructor
public class PrinciperDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    //시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("123123123123123123123123123123 : "+username);
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("can't find " + username));
        System.out.println("userEntity : "+userEntity);
        return new PrincipalDetail(userEntity);
    }
}
