package org.bsuir.labs.spp.service.documents.factories;

import org.bsuir.labs.spp.domain.TaskRevision;
import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.service.documents.dto.TaskRevisionStatisticDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRevisionDocumentFactory implements DocumentDataFactory<TaskRevisionStatisticDTO> {
    private List<TaskRevision> taskRevisions;

    public TaskRevisionDocumentFactory(List<TaskRevision> taskRevisions) {
        this.taskRevisions = taskRevisions;

        this.taskRevisions.sort(Comparator.comparing(TaskRevision::getUpdatedAt));
    }

    @Override
    public List<TaskRevisionStatisticDTO> getData(){
        List<TaskRevisionStatisticDTO> result = new ArrayList<>();
        for (TaskRevision taskRevision : taskRevisions) {
            result.add(getTaskRevisionStatistic(taskRevision));
        }
        return result;
    }

    private TaskRevisionStatisticDTO getTaskRevisionStatistic(TaskRevision taskRevision){
        TaskRevisionStatisticDTO result = new TaskRevisionStatisticDTO();
        result.setUpdateAt(taskRevision.getUpdatedAt());
        result.setName(taskRevision.getName());
        if (taskRevision.getProject() != null) {
            result.setProject(taskRevision.getProject().getName());
        }
        if (taskRevision.getSprint() != null) {
            result.setSprint(taskRevision.getSprint().getName());
        }
        if (taskRevision.getAssignee() != null) {
            result.setAssignee(taskRevision.getAssignee().getLogin());
        }
        result.setDescription(taskRevision.getDescription());
        result.setStatus(taskRevision.getStatus());
        result.setPriority(taskRevision.getPriority());

        return result;
    }

}
