package com.daiane.todosimple.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daiane.security.UserSpringSecurity;
import com.daiane.todosimple.models.User;
import com.daiane.todosimple.repositories.UserRepository;

@Service
public class UserDatailServiceImp implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findByUsername(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("Usuério não encontrado! " + username);
    }
    return new UserSpringSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
  }

}
