package com.daiane.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.daiane.todosimple.models.Task;

@Repository
public interface TaskRepositoriy extends JpaRepository<Task, Long> {

  List<Task> findByUser_Id(Long id);

}
