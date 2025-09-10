package com.aviralproject.TaskManager.repository;


import com.aviralproject.TaskManager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

}
