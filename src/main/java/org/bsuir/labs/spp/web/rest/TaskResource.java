package org.bsuir.labs.spp.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.security.AuthoritiesConstants;
import org.bsuir.labs.spp.security.SecurityUtils;
import org.bsuir.labs.spp.service.TaskService;
import org.bsuir.labs.spp.service.UserService;
import org.bsuir.labs.spp.web.rest.util.HeaderUtil;
import org.bsuir.labs.spp.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Security;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TaskResource {

    private static final String ENTITY_NAME = "task";

    private final TaskService taskService;
    private final UserService userService;

    public TaskResource(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping("/tasks")
    @Timed
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws URISyntaxException {
        if (task.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new task cannot already have an ID")).body(null);
        }
        if (task.getAuthor() == null) {
            String login = SecurityUtils.getCurrentUserLogin();
            userService.getUserWithAuthoritiesByLogin(login).map(task::author);
        }
        Task result = taskService.save(task);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tasks")
    @Timed
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) throws URISyntaxException {
        if (task.getId() == null) {
            return createTask(task);
        }
        Task result = taskService.save(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    @GetMapping("/tasks")
    @Timed
    public ResponseEntity<List<Task>> getAllTasks(@ApiParam Pageable pageable,
                                                  @RequestParam(name = "parentTask", required = false) Long parentTaskId,
                                                  @RequestParam(name = "sprint", required = false) Long sprintId,
                                                  @RequestParam(name = "project", required = false) Long projectId) {

        Page<Task> page;
        if (parentTaskId != null) {
            page = taskService.findAllByParentTaskId(pageable, parentTaskId);
        } else if (sprintId != null) {
            page = taskService.findAllBySprintId(pageable, sprintId);
        } else if (projectId != null) {
            page = taskService.findAllByProjectId(pageable, projectId);
        } else {
            page = taskService.findAll(pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tasks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    @Timed
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = taskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(task));
    }

    @DeleteMapping("/tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Task task = taskService.findOne(id);
            String login = SecurityUtils.getCurrentUserLogin();
            if (!task.getProject().getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to delete this task")).body(null);
            }
        }
        taskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
