package org.bsuir.labs.spp.service;

import org.bsuir.labs.spp.domain.Project;
import org.bsuir.labs.spp.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project save(Project project) {
        Project result = projectRepository.save(project);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        Page<Project> result = projectRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Project findOne(Long id) {
        Project project = projectRepository.findOneWithEagerRelationships(id);
        return project;
    }

    public void delete(Long id) {
        projectRepository.delete(id);
    }
}
