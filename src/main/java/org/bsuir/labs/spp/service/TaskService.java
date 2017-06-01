package org.bsuir.labs.spp.service;

import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.domain.TaskRevision;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.repository.TaskRevisionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskRevisionRepository taskRevisionRepository;

    public TaskService(TaskRepository taskRepository, TaskRevisionRepository taskRevisionRepository) {
        this.taskRepository = taskRepository;
        this.taskRevisionRepository = taskRevisionRepository;
    }

    public Task save(Task task) {
        Task result = taskRepository.save(task);
        taskRevisionRepository.save(new TaskRevision(task));
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        Page<Task> result = taskRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Task findOne(Long id) {
        Task task = taskRepository.findOneWithEagerRelationships(id);
        return task;
    }

    public void delete(Long id) {
        taskRepository.delete(id);
        taskRevisionRepository.delete(taskRevisionRepository.findByTaskId(id));
    }
}
