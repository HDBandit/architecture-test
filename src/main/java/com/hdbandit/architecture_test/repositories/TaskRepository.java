package com.hdbandit.architecture_test.repositories;

import com.hdbandit.architecture_test.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
