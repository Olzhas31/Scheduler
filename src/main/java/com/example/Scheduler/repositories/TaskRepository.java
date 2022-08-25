package com.example.Scheduler.repositories;

import com.example.Scheduler.domains.Task;
import com.example.Scheduler.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByUser(User user);

}
