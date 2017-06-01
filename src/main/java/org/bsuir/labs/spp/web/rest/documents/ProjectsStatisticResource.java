package org.bsuir.labs.spp.web.rest.documents;

import org.bsuir.labs.spp.domain.Project;
import org.bsuir.labs.spp.repository.ProjectRepository;
import org.bsuir.labs.spp.repository.ProjectRepository;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.service.documents.factories.ProjectsDocumentFactory;
import org.bsuir.labs.spp.service.documents.interfaces.ProjectsDocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/documents/projects")
public class ProjectsStatisticResource {
    private final ProjectsDocumentService documentService;
    private final ProjectRepository projectRepository;

    public ProjectsStatisticResource(ProjectRepository projectRepository, ProjectsDocumentService documentService) {
        this.projectRepository = projectRepository;
        this.documentService = documentService;
    }

    @GetMapping("/pdf")
    public void getPdf(HttpServletResponse response) {
        List<Project> projects = projectRepository.findAllWithEagerRelationships();
        ProjectsDocumentFactory factory = new ProjectsDocumentFactory(projects);
        documentService.generatePdf(factory.getData(), true, response);
    }

    @GetMapping("/csv")
    public void getCsv(HttpServletResponse response) {
        List<Project> projects = projectRepository.findAllWithEagerRelationships();
        ProjectsDocumentFactory factory = new ProjectsDocumentFactory(projects);
        documentService.generateCsv(factory.getData(), response);
    }

    @GetMapping("/xls")
    public void getXls(HttpServletResponse response) {
        List<Project> projects = projectRepository.findAllWithEagerRelationships();
        ProjectsDocumentFactory factory = new ProjectsDocumentFactory(projects);
        documentService.generateXls(factory.getData(), response);
    }
}
