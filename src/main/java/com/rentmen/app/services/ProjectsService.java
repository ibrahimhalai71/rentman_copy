package com.rentmen.app.services;

import com.rentmen.app.DTO.ProjectsDto;
import java.util.List;

public interface ProjectsService {

    /**
     * Retrieve all projects.
     * @return list of ProjectsDto
     */
    List<ProjectsDto> getAllProjects();

    /**
     * Retrieve a single project by its ID.
     * @param id the project ID
     * @return the matching ProjectsDto
     */
    ProjectsDto getProjectById(Long id);

    /**
     * Save a new project.
     * @param projectDto the project data
     * @return the saved ProjectsDto
     */
    ProjectsDto saveProject(ProjectsDto projectDto);

    /**
     * Update an existing project.
     * @param id the project ID to update
     * @param projectDto the updated project data
     * @return the updated ProjectsDto
     */
    ProjectsDto updateProject(Long id, ProjectsDto projectDto);

    /**
     * Delete a project by its ID.
     * @param id the project ID
     */
    void deleteProject(Long id);
}
