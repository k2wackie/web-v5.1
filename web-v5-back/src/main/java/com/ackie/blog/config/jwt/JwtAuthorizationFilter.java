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

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("인증이나 권한이 필요한 주소 요청이 됨");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader: " + jwtHeader);


        if(jwtHeader != null || jwtHeader.startsWith("Bearer")) {
            String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
            System.out.println(jwtToken);
            try {
                String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
                System.out.println(username);
                if (username != null) {
                    System.out.println("username 정상");
                    User userEntity = userRepository.findByUsername(username);
                    System.out.println("userEntity: " + userEntity.getUsername());
                    PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
                    System.out.println("principalDetails: " + principalDetails.getUsername());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
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
        }
    }
}

