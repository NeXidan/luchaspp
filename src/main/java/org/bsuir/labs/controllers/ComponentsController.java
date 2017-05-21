package org.bsuir.labs.controllers;


import org.bsuir.labs.controllers.contracts.BasicController;
import org.bsuir.labs.entities.ComponentsEntity;
import org.bsuir.labs.repositories.ComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components")
public class ComponentsController extends BasicController<ComponentsEntity> {

    @Autowired
    public ComponentsRepository repository;

    @GetMapping("/")
    public List list() {
        return repository.findAll();
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<ComponentsEntity> store(@RequestBody ComponentsEntity entity) {
        if (repository.existsByName(entity.getName())) {
            return error(HttpStatus.CONFLICT);
        }
        repository.saveAndFlush(entity);
        return success(entity);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ComponentsEntity> update(@PathVariable("id") Integer id, @RequestBody ComponentsEntity entity) {
        ComponentsEntity dbEntity = repository.findOne(id);
        if (null == dbEntity) {
            return error(HttpStatus.NOT_FOUND);
        }

        dbEntity.setName(entity.getName());

        repository.save(dbEntity);

        return success(dbEntity);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<ComponentsEntity> delete(@PathVariable("id") Integer id) {
        if (!repository.exists(id)) {
            return error(HttpStatus.NOT_FOUND);
        }

        repository.delete(id);
        return success(null);
    }

}
