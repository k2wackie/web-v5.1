package com.ackie.blog.config.jwt;

import com.ackie.blog.config.auth.PrincipalDetails;
import com.ackie.blog.model.User;
import com.ackie.blog.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

// 시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter라는 것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 거치게 되어 있음
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안 거치게 됨
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 거치게 됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("인증이나 권한이 필요한 주소 요청이 됨");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader: " + jwtHeader);

        // jwtHeader가 있는지, Bearer로 시작하는지 확인
        if(jwtHeader != null || jwtHeader.startsWith("Bearer")) {


            String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
            System.out.println(jwtToken);
            try {
                //JWT토큰을 검증을 해서 정상적인 사용자인지 확인
                String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
                System.out.println(username);

                //서명이 정상적으로 됨
                if (username != null) {
                    System.out.println("username 정상");

                    User userEntity = userRepository.findByUsername(username);
                    System.out.println("userEntity: " + userEntity.getUsername());

                    PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
                    System.out.println("principalDetails: " + principalDetails.getUsername());
                    // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 줌
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                    // 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    chain.doFilter(request, response);
                }


            } catch (JWTVerificationException e) {
                logger.error("an error occured during getting username from token", e);
                System.out.println("Verify Error");
//                e.printStackTrace();
                e.getMessage();

                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json;charset=UTF-8");
                String result = "err";
                String json = objectMapper.writeValueAsString(result);
                response.getWriter().print(json);
                response.getWriter().flush();

            }
            logger.warn("couldn't find bearer string, will ignore the header");
//            chain.doFilter(request, response);
//            return;

        }
    }
}

