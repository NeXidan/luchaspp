package org.bsuir.labs.controllers;


import org.bsuir.labs.controllers.contracts.BasicController;
import org.bsuir.labs.entities.ProjectsEntity;
import org.bsuir.labs.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController extends BasicController<ProjectsEntity> {

    @Autowired
    public ProjectRepository repository;

    @GetMapping("/")
    public List list() {
        return repository.findAll();
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<ProjectsEntity> store(@RequestBody ProjectsEntity entity) {
        if (repository.existsByName(entity.getName())) {
            return error(HttpStatus.CONFLICT);
        }
        repository.saveAndFlush(entity);
        return success(entity);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ProjectsEntity> update(@PathVariable("id") Integer id, @RequestBody ProjectsEntity entity) {
        ProjectsEntity dbEntity = repository.findOne(id);
        if (null == dbEntity) {
            return error(HttpStatus.NOT_FOUND);
        }

        dbEntity.setCreatedAt(entity.getCreatedAt());
        dbEntity.setDescription(entity.getDescription());
        dbEntity.setName(entity.getName());
        dbEntity.setStatus(entity.getStatus());

        repository.save(dbEntity);

        return success(dbEntity);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectsEntity> delete(@PathVariable("id") Integer id) {
        if (!repository.exists(id)) {
            return error(HttpStatus.NOT_FOUND);
        }

        repository.delete(id);
        return success(null);
    }

}
