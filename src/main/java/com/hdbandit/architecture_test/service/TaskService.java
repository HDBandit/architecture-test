package com.hdbandit.architecture_test.service;

import com.hdbandit.architecture_test.exception.TaskNotFoundException;
import com.hdbandit.architecture_test.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO findById(long id) throws TaskNotFoundException;

    TaskDTO deleteById(long id) throws TaskNotFoundException;

    List<TaskDTO> findAll();

    TaskDTO update(TaskDTO taskDTO) throws TaskNotFoundException;

    TaskDTO create(TaskDTO taskDTO);

}
