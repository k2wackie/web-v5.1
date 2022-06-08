package com.ackie.blog.config.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ackie.blog.config.auth.PrincipalDetails;
import com.ackie.blog.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송하면(post)
// UsernamePasswordAuthenticationFilter 동작을 함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter: 로그인 시도 중");

        // 1. username, password를 받아서
        // 2. 정상인지 로그인 시도를 해봄, AuthenticationManager로 로그인 시도를 하면 PrincipalDetailService가 실행됨 -> loadUserByUsername 호출
        // -> PrincipalDetails 호출
        // 3. PrincipalDetails를 세션에 담고(권한관리를 위해)
        // 4. JWT 토큰을 만들어서 응답해주면 됨

        try {
            //json parsing
//            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            //토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            //PrincipalDetailService의 loadUserByUsername 함수가 실행된 후 정상이면 authentication 리턴됨
            //DB에 있는 username과 password가 일치함
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료 : " + principalDetails.getUser().getUsername()); //로그인 정상적으로 되었다는 뜻
            //authentication 객체가 session영역에 저장됨
            //리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하기 진행하기 위해...
            //굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 단지 권한 처리 때문에 session에 넣어줌

            return authentication; //session에 저장
        } catch (IOException e) {
//			throw new RuntimeException(e);
            e.printStackTrace();
        }
        System.out.println("==================================================");

        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    //JWT토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("successfulAuthentication 실행됨: 인증 완료");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA방식은 아니고, Hash암호방식(HMAC512) 일반적으로 많이 사용
        String jwtToken = JWT.create()
                .withSubject("team515")
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))  //토큰 만료 시간
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        System.out.println("jwtToken :  " + jwtToken);
        response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX+jwtToken);


        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        String result = "{\"code\" : \""+HttpStatus.OK.value()+"\", \"msg\" : \"success\" }";
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}

