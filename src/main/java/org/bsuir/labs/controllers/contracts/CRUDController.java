package org.bsuir.labs.controllers.contracts;

import org.bsuir.labs.repositories.EntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CRUDController<T> extends BasicController<T> {
    protected EntityRepository<T> repository;

    public List list() {
        return this.repository.findAll();
    }

    public ResponseEntity<T> create(T entity) {
        // TODO: conflict
        this.repository.saveAndFlush(entity);
        return this.success(entity);
    }

    public ResponseEntity<T> read(Integer id) {
        T entity = this.repository.findOne(id);
        if (entity == null) {
            return this.error(HttpStatus.NOT_FOUND);
        }
        return this.success(entity);
    }

    public ResponseEntity<T> update(Integer id, T entity) {
        T dbEntity = this.repository.findOne(id);
        if (dbEntity == null) {
            return this.error(HttpStatus.NOT_FOUND);
        }

        this.repository.save(entity);
        return this.success(entity);
    }

    public ResponseEntity<T> delete(Integer id) {
        if (!this.repository.exists(id)) {
            return error(HttpStatus.NOT_FOUND);
        }

        this.repository.delete(id);
        return this.success(null);
    }
}
