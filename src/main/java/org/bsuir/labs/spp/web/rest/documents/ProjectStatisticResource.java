package org.bsuir.labs.spp.web.rest.documents;

import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.service.documents.factories.ProjectDocumentFactory;
import org.bsuir.labs.spp.service.documents.interfaces.ProjectDocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/documents/project")
public class ProjectStatisticResource {
    private final ProjectDocumentService documentService;
    private final TaskRepository taskRepository;

    public ProjectStatisticResource(TaskRepository taskRepository, ProjectDocumentService documentService) {
        this.taskRepository = taskRepository;
        this.documentService = documentService;
    }

    @GetMapping("/pdf/{id}")
    public void getPdf(HttpServletResponse response, @PathVariable("id") Long id) {
        List<Task> tasks = taskRepository.findByProjectId(id);
        ProjectDocumentFactory factory = new ProjectDocumentFactory(tasks);
        documentService.generatePdf(factory.getData(), true, response);
    }

    @GetMapping("/csv/{id}")
    public void getCsv(HttpServletResponse response, @PathVariable("id") Long id) {
        List<Task> tasks = taskRepository.findByProjectId(id);
        ProjectDocumentFactory factory = new ProjectDocumentFactory(tasks);
        documentService.generateCsv(factory.getData(), response);
    }

    @GetMapping("/xls/{id}")
    public void getXls(HttpServletResponse response, @PathVariable("id") Long id) {
        List<Task> tasks = taskRepository.findByProjectId(id);
        ProjectDocumentFactory factory = new ProjectDocumentFactory(tasks);
        documentService.generateXls(factory.getData(), response);
    }
}
