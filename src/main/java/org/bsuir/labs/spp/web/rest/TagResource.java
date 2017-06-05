package org.bsuir.labs.spp.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.domain.Tag;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.security.AuthoritiesConstants;
import org.bsuir.labs.spp.security.SecurityUtils;
import org.bsuir.labs.spp.service.TagService;
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
public class TagResource {

    private static final String ENTITY_NAME = "tag";

    private final TagService tagService;

    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    @Timed
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
        if (tag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tag cannot already have an ID")).body(null);
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            String login = SecurityUtils.getCurrentUserLogin();
            if (!tag.getProject().getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to create tag")).body(null);
            }
        }
        Tag result = tagService.save(tag);
        return ResponseEntity.created(new URI("/api/tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tags")
    @Timed
    public ResponseEntity<Tag> updateTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
        if (tag.getId() == null) {
            return createTag(tag);
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Tag tagEntity = tagService.findOne(tag.getId());
            String login = SecurityUtils.getCurrentUserLogin();
            if (!tagEntity.getProject().getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to edit tag")).body(null);
            }
        }
        Tag result = tagService.save(tag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tag.getId().toString()))
            .body(result);
    }

    @GetMapping("/tags")
    @Timed
    public ResponseEntity<List<Tag>> getAllTags(@ApiParam Pageable pageable,
                                                @RequestParam(name = "project", required = false) Long projectId) {
        if (projectId != null) {
            List<Tag> tags = tagService.findAllByProjectId(projectId);
            return ResponseEntity.ok().body(tags);
        }

        Page<Tag> page = tagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/tags/{id}")
    @Timed
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        Tag tag = tagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tag));
    }

    @DeleteMapping("/tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Tag tagEntity = tagService.findOne(id);
            String login = SecurityUtils.getCurrentUserLogin();
            if (!tagEntity.getProject().getManagers().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "norights", "You have no rights to delete tag")).body(null);
            }
        }
        tagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
