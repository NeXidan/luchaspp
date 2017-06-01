package org.bsuir.labs.spp.web.rest.documents;

import org.bsuir.labs.spp.domain.TaskRevision;
import org.bsuir.labs.spp.repository.TaskRevisionRepository;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.repository.TaskRevisionRepository;
import org.bsuir.labs.spp.service.documents.factories.TaskRevisionDocumentFactory;
import org.bsuir.labs.spp.service.documents.interfaces.TaskRevisionDocumentService;
import org.bsuir.labs.spp.service.documents.interfaces.TaskRevisionDocumentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/documents/task")
public class TaskRevisionStatisticResource {
    private final TaskRevisionDocumentService documentService;
    private final TaskRevisionRepository taskRevisionRepository;

    public TaskRevisionStatisticResource(TaskRevisionRepository taskRevisionRepository, TaskRevisionDocumentService documentService) {
        this.taskRevisionRepository = taskRevisionRepository;
        this.documentService = documentService;
    }

    @GetMapping("/pdf/{id}")
    public void getPdf(HttpServletResponse response, @PathVariable("id") Long id) {
        List<TaskRevision> taskRevisions = taskRevisionRepository.findByTaskId(id);
        TaskRevisionDocumentFactory factory = new TaskRevisionDocumentFactory(taskRevisions);
        documentService.generatePdf(factory.getData(), true, response);
    }

    @GetMapping("/csv/{id}")
    public void getCsv(HttpServletResponse response, @PathVariable("id") Long id) {
        List<TaskRevision> taskRevisions = taskRevisionRepository.findByTaskId(id);
        TaskRevisionDocumentFactory factory = new TaskRevisionDocumentFactory(taskRevisions);
        documentService.generateCsv(factory.getData(), response);
    }

    @GetMapping("/xls/{id}")
    public void getXls(HttpServletResponse response, @PathVariable("id") Long id) {
        List<TaskRevision> taskRevisions = taskRevisionRepository.findByTaskId(id);
        TaskRevisionDocumentFactory factory = new TaskRevisionDocumentFactory(taskRevisions);
        documentService.generateXls(factory.getData(), response);
    }
}
