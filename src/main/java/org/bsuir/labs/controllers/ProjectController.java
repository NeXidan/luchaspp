package org.bsuir.labs.controllers;


import org.bsuir.labs.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    public ProjectRepository repository;

    @GetMapping("/list")
    public List list() {
         return repository.findAll();
    }

}
