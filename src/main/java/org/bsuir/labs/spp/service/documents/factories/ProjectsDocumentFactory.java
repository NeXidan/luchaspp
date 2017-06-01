package org.bsuir.labs.spp.service.documents.factories;

import org.bsuir.labs.spp.domain.Project;
import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.service.documents.dto.ProjectStatisticDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectsDocumentFactory implements DocumentDataFactory<ProjectStatisticDTO> {
    private List<Project> projects;

    public ProjectsDocumentFactory(List<Project> projects){
        this.projects = projects;
    }

    @Override
    public List<ProjectStatisticDTO> getData(){
        List<ProjectStatisticDTO> result = new ArrayList<>();
        for (Project project : projects) {
            result.add(getProjectStatistic(project));
        }
        return result;
    }

    private ProjectStatisticDTO getProjectStatistic(Project project){
        ProjectStatisticDTO result = new ProjectStatisticDTO();
        result.setId(project.getId());
        result.setName(project.getName());
        result.setDescription(project.getDescription());
        result.setStatus(project.getStatus());
        result.setManagers(project.getManagers().stream().map(User::getLogin).collect(Collectors.toList()));

        return result;
    }

}
