package com.hdbandit.architecture_test.controller;

import com.hdbandit.architecture_test.dto.TaskDTO;
import com.hdbandit.architecture_test.exception.TaskNotFoundException;
import com.hdbandit.architecture_test.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Api(basePath = "/task", description = "Operations with tasks", produces = "application/json")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    //-------------------List all tasks Task --------------------------------------------------------

    @ApiOperation(value = "Find all stored tasks")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "") })
    @RequestMapping(value = "/task/", method = GET)
    public ResponseEntity<List<TaskDTO>> findAllTasks() {
        List<TaskDTO> tasks = taskService.findAll();
        return new ResponseEntity<List<TaskDTO>>(tasks, OK);
    }

    //-------------------Retrieve Single Task --------------------------------------------------------

    @ApiOperation(value = "Get a task associated to the given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "If the task with this id, doesn't exist")
    })
    @RequestMapping(value = "/task/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDTO> getTask(@PathVariable("id") long id) {
        TaskDTO task = null;
        try {
            task = taskService.findById(id);
            return new ResponseEntity<TaskDTO>(task, OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<TaskDTO>(NOT_FOUND);
        }
    }

    //-------------------Create a Task--------------------------------------------------------

    @ApiOperation(value = "Create a task with the given id and description.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/task/", method = POST)
    public ResponseEntity<Void> createTask(@RequestBody TaskDTO taskDTO, UriComponentsBuilder ucBuilder) {
        TaskDTO task = taskService.create(taskDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/task/{id}").buildAndExpand(task.getId()).toUri());
        return new ResponseEntity<Void>(headers, CREATED);
    }


    //------------------- Update a Task --------------------------------------------------------

    @ApiOperation(value = "Update a task associated to the given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "If the task to update doesn't exist")
    })
    @RequestMapping(value = "/task/{id}", method = PUT)
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO currentTask = null;
        try {
            currentTask = taskService.findById(id);
            taskService.update(taskDTO);
            return new ResponseEntity<TaskDTO>(taskDTO, OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<TaskDTO>(NOT_FOUND);
        }
    }

    //------------------- Delete a Task --------------------------------------------------------

    @ApiOperation(value = "Delete a task associated to the given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "If the task to delete doesn't exist")
    })
    @RequestMapping(value = "/task/{id}", method = DELETE)
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable("id") long id) {
        TaskDTO task = null;
        try {
            task = taskService.findById(id);
            taskService.deleteById(id);
            return new ResponseEntity<TaskDTO>(NO_CONTENT);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<TaskDTO>(NOT_FOUND);
        }
    }

}
