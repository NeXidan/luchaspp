package org.bsuir.labs.controllers;

import org.bsuir.labs.controllers.contracts.CRUDController;
import org.bsuir.labs.entities.ProjectsEntity;
import org.bsuir.labs.repositories.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/projects")
public class ProjectsController extends CRUDController<ProjectsEntity> {
    @Autowired
    protected ProjectsRepository repository;

    @Override
    @GetMapping("/")
    public List list() {
        return super.list();
    }

    @Override
    @Transactional
    @PostMapping("/")
    public ResponseEntity<ProjectsEntity> create(@RequestBody ProjectsEntity entity) {
        return super.create(entity);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProjectsEntity> read(@PathVariable("id") Integer id) {
        return super.read(id);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ProjectsEntity> update(@PathVariable("id") Integer id, @RequestBody ProjectsEntity entity) {
        return super.update(id, entity);
    }

    @Override
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectsEntity> delete(@PathVariable("id") Integer id) {
        return super.delete(id);
    }
}
