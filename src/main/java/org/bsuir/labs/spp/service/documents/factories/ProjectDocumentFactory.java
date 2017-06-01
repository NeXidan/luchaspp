package org.bsuir.labs.spp.service.documents.factories;

import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.service.documents.dto.TaskStatisticDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProjectDocumentFactory implements DocumentDataFactory<TaskStatisticDTO> {
    private List<Task> tasks;

    public ProjectDocumentFactory(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public List<TaskStatisticDTO> getData(){
        List<TaskStatisticDTO> result = new ArrayList<>();
        for (Task task : tasks) {
            result.add(getTaskStatistic(task));
        }
        return result;
    }

    private TaskStatisticDTO getTaskStatistic(Task task){
        TaskStatisticDTO result = new TaskStatisticDTO();
        result.setId(task.getId());
        result.setName(task.getName());
        if (task.getProject() != null) {
            result.setProject(task.getProject().getName());
        }
        if (task.getSprint() != null) {
            result.setSprint(task.getSprint().getName());
        }
        if (task.getAssignee() != null) {
            result.setAssignee(task.getAssignee().getLogin());
        }
        result.setDescription(task.getDescription());
        result.setStatus(task.getStatus());
        result.setPriority(task.getPriority());

        return result;
    }

}
