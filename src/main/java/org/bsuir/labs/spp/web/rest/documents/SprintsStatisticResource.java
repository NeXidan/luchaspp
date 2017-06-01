package org.bsuir.labs.spp.web.rest.documents;

import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.repository.SprintRepository;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.repository.SprintRepository;
import org.bsuir.labs.spp.service.documents.factories.SprintsDocumentFactory;
import org.bsuir.labs.spp.service.documents.interfaces.SprintDocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/documents/sprints")
public class SprintsStatisticResource {
    private final SprintDocumentService documentService;
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;

    public SprintsStatisticResource(SprintRepository sprintRepository, TaskRepository taskRepository, SprintDocumentService documentService) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.documentService = documentService;
    }

    @GetMapping("/pdf")
    public void getPdf(HttpServletResponse response) {
        List<Sprint> sprints = sprintRepository.findAll();
        SprintsDocumentFactory factory = new SprintsDocumentFactory(sprints, taskRepository);
        documentService.generatePdf(factory.getData(), true, response);
    }

    @GetMapping("/csv")
    public void getCsv(HttpServletResponse response) {
        List<Sprint> sprints = sprintRepository.findAll();
        SprintsDocumentFactory factory = new SprintsDocumentFactory(sprints, taskRepository);
        documentService.generateCsv(factory.getData(), response);
    }

    @GetMapping("/xls")
    public void getXls(HttpServletResponse response) {
        List<Sprint> sprints = sprintRepository.findAll();
        SprintsDocumentFactory factory = new SprintsDocumentFactory(sprints, taskRepository);
        documentService.generateXls(factory.getData(), response);
    }
}
