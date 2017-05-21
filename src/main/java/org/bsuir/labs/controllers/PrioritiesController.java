package org.bsuir.labs.controllers;

import org.bsuir.labs.controllers.contracts.CRUDController;
import org.bsuir.labs.entities.PrioritiesEntity;
import org.bsuir.labs.repositories.PrioritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priorities")
public class PrioritiesController extends CRUDController<PrioritiesEntity> {
    @Autowired
    protected PrioritiesRepository repository;

    @Override
    @GetMapping("/")
    public List list() {
        return super.list();
    }

    @Override
    @Transactional
    @PostMapping("/")
    public ResponseEntity<PrioritiesEntity> create(@RequestBody PrioritiesEntity entity) {
        return super.create(entity);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PrioritiesEntity> read(@PathVariable("id") Integer id) {
        return super.read(id);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<PrioritiesEntity> update(@PathVariable("id") Integer id, @RequestBody PrioritiesEntity entity) {
        return super.update(id, entity);
    }

    @Override
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<PrioritiesEntity> delete(@PathVariable("id") Integer id) {
        return super.delete(id);
    }
}
