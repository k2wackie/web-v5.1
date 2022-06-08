package com.ackie.blog.config.auth;

import com.ackie.blog.model.User;
import com.ackie.blog.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
    // password 부분 처리는 알아서 함
    // username이 DB에 있는지만 확인해주면 됨
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: " + username);
        User userEntity = userRepository.findByUsername(username);
        System.out.println("userEntity: " + userEntity.toString());
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }

        System.out.println("user를 찾을 수 없습니다.");
        return null;
    }
}
