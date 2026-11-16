package com.rentmen.app.controllers;

import com.rentmen.app.DTO.ProjectsDto;
import com.rentmen.app.services.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @GetMapping
    public List<ProjectsDto> getAllProjects() {
        return projectsService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectsDto getProjectById(@PathVariable Long id) {
        return projectsService.getProjectById(id);
    }

    @PostMapping
    public ProjectsDto createProject(@RequestBody ProjectsDto projectDto) {
        return projectsService.saveProject(projectDto);
    }

    @PutMapping("/{id}")
    public ProjectsDto updateProject(@PathVariable Long id, @RequestBody ProjectsDto projectDto) {
        return projectsService.updateProject(id, projectDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectsService.deleteProject(id);
    }
}
