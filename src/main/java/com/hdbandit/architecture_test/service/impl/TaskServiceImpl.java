package com.hdbandit.architecture_test.service.impl;

import com.hdbandit.architecture_test.exception.TaskNotFoundException;
import com.hdbandit.architecture_test.service.TaskService;
import com.hdbandit.architecture_test.dto.TaskDTO;
import com.hdbandit.architecture_test.model.Task;
import com.hdbandit.architecture_test.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDTO findById(long id) throws TaskNotFoundException {
        checkTaskExist(id);
        return convert(taskRepository.findOne(id));
    }

    @Override
    public TaskDTO deleteById(long id) throws TaskNotFoundException {
        Task task = checkTaskExist(id);
        taskRepository.delete(id);
        return convert(task);
    }

    @Override
    public List<TaskDTO> findAll() {
        return stream(taskRepository.findAll().spliterator(), false).map(this::convert).collect(toList());
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) throws TaskNotFoundException {
        Task task = checkTaskExist(taskDTO.getId());
        task.setDescription(taskDTO.getDescription());
        return convert(taskRepository.save(task));
    }

    @Override
    public TaskDTO create(TaskDTO taskDTO) {
        return convert(taskRepository.save(new Task(taskDTO.getDescription())));
    }

    private TaskDTO convert(Task task) {
        return new TaskDTO(task.getId(), task.getDescription());
    }

    private Task checkTaskExist(long id) throws TaskNotFoundException {
        Task task = taskRepository.findOne(id);
        if (task == null) throw new TaskNotFoundException(String.format("Task with id: %s not found", id));
        return task;
    }
}
