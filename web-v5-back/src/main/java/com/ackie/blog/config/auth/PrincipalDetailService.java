package com.ackie.blog.config.auth;

import com.ackie.blog.model.User;
import com.ackie.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;
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
