package com.daiane.todosimple.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.daiane.todosimple.models.ProfileEnum;
import com.daiane.todosimple.models.User;
import com.daiane.todosimple.repositories.UserRepository;
import com.daiane.todosimple.services.exeptions.DataBindingViolationException;
import com.daiane.todosimple.services.exeptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserRepository userRepository;

  public User findById(Long id) {
    Optional<User> user = this.userRepository.findById(id);
    return user.orElseThrow(
        () -> new ObjectNotFoundException("Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
  }

  @Transactional
  public User create(User obj) {
    obj.setId(null);
    obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
    obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
    obj = this.userRepository.save(obj);
    return obj;
  }

  @Transactional
  public User update(User obj) {
    User newObj = this.findById(obj.getId());
    newObj.setPassword(obj.getPassword());
    newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
    return this.userRepository.save(newObj);
  }

  public void delete(Long id) {
    findById(id);
    try {
      this.userRepository.deleteById(id);
    } catch (Exception e) {
      throw new DataBindingViolationException("Não é possível excluir, pois há entidades relacionadas!");
    }
  }

}
