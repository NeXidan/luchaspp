package org.bsuir.labs.controllers;


import org.bsuir.labs.controllers.contracts.BasicController;
import org.bsuir.labs.entities.PrioritiesEntity;
import org.bsuir.labs.repositories.PrioritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priorities")
public class PrioritiesController extends BasicController<PrioritiesEntity> {

    @Autowired
    public PrioritiesRepository repository;

    @GetMapping("/")
    public List list() {
        return repository.findAll();
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<PrioritiesEntity> store(@RequestBody PrioritiesEntity entity) {
        if (repository.existsByName(entity.getName())) {
            return error(HttpStatus.CONFLICT);
        }
        repository.saveAndFlush(entity);
        return success(entity);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<PrioritiesEntity> update(@PathVariable("id") Integer id, @RequestBody PrioritiesEntity entity) {
        PrioritiesEntity dbEntity = repository.findOne(id);
        if (null == dbEntity) {
            return error(HttpStatus.NOT_FOUND);
        }

        dbEntity.setName(entity.getName());

        repository.save(dbEntity);

        return success(dbEntity);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<PrioritiesEntity> delete(@PathVariable("id") Integer id) {
        if (!repository.exists(id)) {
            return error(HttpStatus.NOT_FOUND);
        }

        repository.delete(id);
        return success(null);
    }

}
