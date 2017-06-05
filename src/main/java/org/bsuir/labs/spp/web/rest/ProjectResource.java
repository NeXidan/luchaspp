package org.bsuir.labs.spp.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.bsuir.labs.spp.domain.Project;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.security.AuthoritiesConstants;
import org.bsuir.labs.spp.security.SecurityUtils;
import org.bsuir.labs.spp.service.ProjectService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProjectResource {

    private static final String ENTITY_NAME = "project";

    private final ProjectService projectService;

    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/projects")
    @Timed
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) throws URISyntaxException {
        if (project.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new project cannot already have an ID")).body(null);
        }
        Project result = projectService.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/projects")
    @Timed
    public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project) throws URISyntaxException {
        if (project.getId() == null) {
            return createProject(project);
        }

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            String login = SecurityUtils.getCurrentUserLogin();
            if (!project.getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to edit this project")).body(null);
            }
        }

        Project result = projectService.save(project);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, project.getId().toString()))
            .body(result);
    }

    @GetMapping("/projects")
    @Timed
    public ResponseEntity<List<Project>> getAllProjects(@ApiParam Pageable pageable) {
        Page<Project> page = projectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(project));
    }

    @DeleteMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Project project = projectService.findOne(id);
            String login = SecurityUtils.getCurrentUserLogin();
            if (!project.getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to edit this project")).body(null);
            }
        }

        projectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
