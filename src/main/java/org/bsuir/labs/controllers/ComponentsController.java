package org.bsuir.labs.controllers;

import org.bsuir.labs.controllers.contracts.CRUDController;
import org.bsuir.labs.entities.ComponentsEntity;
import org.bsuir.labs.repositories.ComponentsRepository;
import org.bsuir.labs.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components")
public class ComponentsController extends CRUDController<ComponentsEntity> {
    @Autowired
    public ComponentsController(ComponentsRepository repository) {
        this.repository = repository;
    }

    @Override
    @GetMapping("/")
    public List list() {
        return super.list();
    }

    @Override
    @Transactional
    @PostMapping("/")
    public ResponseEntity<ComponentsEntity> create(@RequestBody ComponentsEntity entity) {
        return super.create(entity);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ComponentsEntity> read(@PathVariable("id") Integer id) {
        return super.read(id);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ComponentsEntity> update(@PathVariable("id") Integer id, @RequestBody ComponentsEntity entity) {
        return super.update(id, entity);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<ComponentsEntity> delete(@PathVariable("id") Integer id) {
        return super.delete(id);
    }
}
