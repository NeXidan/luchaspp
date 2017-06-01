package org.bsuir.labs.spp.web.rest.documents;

import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.repository.UserRepository;
import org.bsuir.labs.spp.service.documents.factories.UsersDocumentFactory;
import org.bsuir.labs.spp.service.documents.interfaces.UserDocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/documents/users")
public class UsersStatisticResource {
    private final UserRepository userRepository;
    private final UserDocumentService documentService;
    private final TaskRepository taskRepository;

    public UsersStatisticResource(UserRepository userRepository, TaskRepository taskRepository, UserDocumentService documentService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.documentService = documentService;
    }

    @GetMapping("/pdf")
    public void getPdf(HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        UsersDocumentFactory factory = new UsersDocumentFactory(users, taskRepository);
        documentService.generatePdf(factory.getData(), true, response);
    }

    @GetMapping("/csv")
    public void getCsv(HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        UsersDocumentFactory factory = new UsersDocumentFactory(users, taskRepository);
        documentService.generateCsv(factory.getData(), response);
    }

    @GetMapping("/xls")
    public void getXls(HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        UsersDocumentFactory factory = new UsersDocumentFactory(users, taskRepository);
        documentService.generateXls(factory.getData(), response);
    }
}
