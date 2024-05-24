package com.daiane.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daiane.todosimple.models.Task;
import com.daiane.todosimple.models.User;
import com.daiane.todosimple.repositories.TaskRepositoriy;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

  @Autowired
  private TaskRepositoriy taskRepositoriy;

  @Autowired
  private UserService userService;

  public Task findById(Long id) {
    Optional<Task> task = this.taskRepositoriy.findById(id);
    return task.orElseThrow(
        () -> new RuntimeException("Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()));
  }

  @Transactional
  public Task create(Task obj) {
    User user = this.userService.findById(obj.getUser().getId());
    obj.setId(null);
    obj.setUser(user);
    obj = this.taskRepositoriy.save(obj);
    return obj;
  }

  @Transactional
  public Task update(Task obj) {
    Task newObj = this.findById(obj.getId());
    newObj.setDescription(obj.getDescription());
    return this.taskRepositoriy.save(newObj);
  }

  public void delete(Long id) {
    findById(id);
    try {
      this.taskRepositoriy.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Não é possível excluir, pois há entidades relacionadas!");
    }
  }
}
