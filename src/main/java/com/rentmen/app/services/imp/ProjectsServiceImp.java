package com.rentmen.app.services.imp;

import com.rentmen.app.DTO.ProjectsDto;
import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.Projects;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.JobRepo;
import com.rentmen.app.repositories.ProjectsRepo;
import com.rentmen.app.services.ProjectsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectsServiceImp implements ProjectsService {

    @Autowired
    private ProjectsRepo projectsRepo;

    @Autowired
    private TwilioMessageService twilioMessageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobRepo jobRepo;

    private ProjectsDto mapToDto(Projects project) {
        return modelMapper.map(project, ProjectsDto.class);
    }

    private Projects mapToEntity(ProjectsDto dto) {
        return modelMapper.map(dto, Projects.class);

    }

    @Override
    public List<ProjectsDto> getAllProjects() {
        return projectsRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectsDto getProjectById(Long id) {
        Projects project = projectsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: ","", id));
        return mapToDto(project);
    }

    @Override
    public ProjectsDto saveProject(ProjectsDto projectDto) {
        Projects project = mapToEntity(projectDto);
        Projects savedProject = projectsRepo.save(project);
        twilioMessageService.sendProjectInfoSMS(projectDto.getId(), projectDto.getName());
        return mapToDto(savedProject);
    }

    @Override
    public ProjectsDto updateProject(Long id, ProjectsDto projectDto) {
        Projects existingProject = projectsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: ","", id));
        existingProject.setName(projectDto.getName());
        existingProject.setDescription(projectDto.getDescription());
        Projects updatedProject = projectsRepo.save(existingProject);
        return mapToDto(updatedProject);
    }

    @Transactional
    @Override
    public void deleteProject(Long projectId) {
        Projects project = projectsRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<Job> jobs = jobRepo.findByProject(project);
        for (Job job : jobs) {
            job.setProject(null);
            jobRepo.save(job);
        }

        projectsRepo.delete(project);
    }
}
