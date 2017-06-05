package org.bsuir.labs.spp.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.domain.Tag;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.security.AuthoritiesConstants;
import org.bsuir.labs.spp.security.SecurityUtils;
import org.bsuir.labs.spp.service.SprintService;
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
public class SprintResource {

    private static final String ENTITY_NAME = "sprint";

    private final SprintService sprintService;

    public SprintResource(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping("/sprints")
    @Timed
    public ResponseEntity<Sprint> createSprint(@Valid @RequestBody Sprint sprint) throws URISyntaxException {
        if (sprint.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sprint cannot already have an ID")).body(null);
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            String login = SecurityUtils.getCurrentUserLogin();
            if (!sprint.getProject().getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to create sprint")).body(null);
            }
        }
        Sprint result = sprintService.save(sprint);
        return ResponseEntity.created(new URI("/api/sprints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/sprints")
    @Timed
    public ResponseEntity<Sprint> updateSprint(@Valid @RequestBody Sprint sprint) throws URISyntaxException {
        if (sprint.getId() == null) {
            return createSprint(sprint);
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Sprint sprintEntity = sprintService.findOne(sprint.getId());
            String login = SecurityUtils.getCurrentUserLogin();
            if (!sprintEntity.getProject().getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to edit this sprint")).body(null);
            }
        }
        Sprint result = sprintService.save(sprint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sprint.getId().toString()))
            .body(result);
    }

    @GetMapping("/sprints")
    @Timed
    public ResponseEntity<List<Sprint>> getAllSprints(@ApiParam Pageable pageable,
                                                      @RequestParam(name = "project", required = false) Long projectId) {
        if (projectId != null) {
            List<Sprint> sprints = sprintService.findAllByProjectId(projectId);
            return ResponseEntity.ok().body(sprints);
        }

        Page<Sprint> page = sprintService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sprints");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/sprints/{id}")
    @Timed
    public ResponseEntity<Sprint> getSprint(@PathVariable Long id) {
        Sprint sprint = sprintService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sprint));
    }

    @DeleteMapping("/sprints/{id}")
    @Timed
    public ResponseEntity<Void> deleteSprint(@PathVariable Long id) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Sprint sprintEntity = sprintService.findOne(id);
            String login = SecurityUtils.getCurrentUserLogin();
            if (!sprintEntity.getProject().getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to delete this sprint")).body(null);
            }
        }
        sprintService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
