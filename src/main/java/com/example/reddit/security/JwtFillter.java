package com.example.reddit.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFillter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwtToken=getJwtToken(request);
        if(StringUtils.hasText(jwtToken) && jwtProvider.validateToken(jwtToken)){
            System.out.println("valid token");
            String userName=jwtProvider.getUsernameFromJwt(jwtToken);
            System.out.println(userName);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            System.out.println(userDetails);
            UsernamePasswordAuthenticationToken authentication =new
                    UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtToken(HttpServletRequest request) {
        String beareToken= request.getHeader("Authorization");
        if(StringUtils.hasText(beareToken) && beareToken.startsWith("Bearer ")){
            return beareToken.substring(7);
        }
        return beareToken;
    }
}
