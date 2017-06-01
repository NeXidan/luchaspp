package org.bsuir.labs.spp.service.documents.factories;

import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.service.documents.dto.SprintStatisticDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SprintsDocumentFactory implements DocumentDataFactory<SprintStatisticDTO> {
    private final TaskRepository taskRepository;
    private List<Sprint> sprints;

    public SprintsDocumentFactory(List<Sprint> sprints, TaskRepository taskRepository){
        this.sprints = sprints;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<SprintStatisticDTO> getData(){
        List<SprintStatisticDTO> result = new ArrayList<>();
        for (Sprint sprint : sprints) {
            result.add(getSprintStatistic(sprint));
        }
        return result;
    }

    private SprintStatisticDTO getSprintStatistic(Sprint sprint){
        SprintStatisticDTO result = new SprintStatisticDTO();
        result.setId(sprint.getId());
        result.setName(sprint.getName());
        result.setProject(sprint.getProject().getName());
        result.setFromDate(sprint.getFromDate());
        result.setToDate(sprint.getToDate());

        List<Task> tasks = taskRepository.findBySprint(sprint);
        result.setTasks(tasks.stream().map(task -> task.getName() + "-" + task.getId()).collect(Collectors.toList()));

        return result;
    }

}
