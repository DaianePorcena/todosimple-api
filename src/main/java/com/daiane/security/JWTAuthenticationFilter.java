package com.daiane.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.daiane.todosimple.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  private JWTUtil jwtUtil;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      User userCredentials = new ObjectMapper().readValue(request.getInputStream(), User.class);

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>());

      Authentication authentication = this.authenticationManager.authenticate(authToken);
      return authentication;
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain,
      Authentication authentication) throws IOException, ServletException {
    UserSpringSecurity userSpringSecurity = (UserSpringSecurity) authentication.getPrincipal();
    String username = userSpringSecurity.getUsername();
    String token = this.jwtUtil.generateToken(username);
    response.addHeader("Authorization", "Bearer" + token);
    response.addHeader("access-control-expose-headers", "Authentication");
  }
}
