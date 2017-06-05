package org.bsuir.labs.spp.service;

import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.repository.SprintRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SprintService {

    private final SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public Sprint save(Sprint sprint) {
        Sprint result = sprintRepository.save(sprint);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Sprint> findAll(Pageable pageable) {
        Page<Sprint> result = sprintRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Sprint findOne(Long id) {
        Sprint sprint = sprintRepository.findOneWithEagerRelationships(id);
        return sprint;
    }

    public void delete(Long id) {
        sprintRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Sprint> findAllByProjectId(Long projectId) {
        return sprintRepository.findAllByProjectId(projectId);
    }
}
