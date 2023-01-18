package com.example.springsecurity.filter;

import com.example.springsecurity.model.User;
import com.example.springsecurity.service.UserDetailService;
import com.example.springsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String authorizationrequest= request.getHeader("Authorization");
       String token=null;
       String username=null;
       // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYXZhdGVjaGllIiwiZXhwIjoxNjc0MDMxNjQxLCJpYXQiOjE2NzM5OTU2NDF9.sVB91HVThByuH0v4m2Ab3xa7MbNJvq5UAZG2vvRXCA8
        if(authorizationrequest!=null&&authorizationrequest.startsWith("Bearer ")){
            token= authorizationrequest.substring(7);
            username=jwtUtil.extractUsername(token);
}
if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
    UserDetails userDetails=service.loadUserByUsername(username);
    if(jwtUtil.validateToken(token,userDetails)){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    }
}
filterChain.doFilter(request,response);
    }
}
