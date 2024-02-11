package com.rentmen.app.services.imp;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentmen.app.DTO.JobDto;
import com.rentmen.app.DTO.UserDto;
import com.rentmen.app.entities.Client;
import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.Moderator;
import com.rentmen.app.entities.ServiceProvider;
import com.rentmen.app.entities.Skill;
import com.rentmen.app.entities.User;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.ClientRepo;
import com.rentmen.app.repositories.JobRepo;
import com.rentmen.app.repositories.ModeratorRepo;
import com.rentmen.app.repositories.ServiceProviderRepo;
import com.rentmen.app.repositories.SkillRepo;
import com.rentmen.app.repositories.UserRepo;
import com.rentmen.app.services.JobService;

@Service
public class JobServiceImp implements JobService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private ClientRepo clientRepo;
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@Autowired
	private ModeratorRepo moderatorRepo; 
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private SkillRepo skillRepo;
	
	@Autowired
	private JobRepo jobRepo;

	@Override
	public JobDto createJob(JobDto jobDto) {
		Job job = setFeilds(jobDto);
		job = jobRepo.save(job);
		return modelMapper.map(job, JobDto.class);
	}

	@Override
	public JobDto updateJob(JobDto jobDto) {
		Job updatedJob = setFeilds(jobDto);
		Job oldJob= jobRepo.findById(jobDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobDto.getId()));
		updatedJob.setId(oldJob.getId());
		updatedJob = jobRepo.save(updatedJob);
		return modelMapper.map(updatedJob, JobDto.class);
	}

	@Override
	public JobDto getJobById(Long id) {
		Job job= jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
		return modelMapper.map(job, JobDto.class);
	}

	@Override
	public List<JobDto> getAllJobs() {
		List<Job> jobList = jobRepo.findAll();
		Type listType = new TypeToken<List<JobDto>>() {}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public Boolean deleteJob(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobDto> getAllJobsByClientId(Long id) {
		List<Job> jobList = jobRepo.findByClientId(id);
		Type listType = new TypeToken<List<JobDto>>() {}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public List<JobDto> getAllJobsByModeratorId(Long id) {
		List<Job> jobList = jobRepo.findByModeratorId(id);
		Type listType = new TypeToken<List<JobDto>>() {}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public List<JobDto> getAllJobsServiceProviderId(Long id) {
		List<Job> jobList = jobRepo.findByServiceProviderId(id);
		Type listType = new TypeToken<List<JobDto>>() {}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public List<JobDto> getAllJobsCreatedById(Long id) {
		List<Job> jobList = jobRepo.findByCreatedBy(id);
		Type listType = new TypeToken<List<JobDto>>() {}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public List<JobDto> getPotentialJobsOfferedToServiceProvider(Set<Long> serviceProviderIds) {
		Set<ServiceProvider> potentialServiceProviders = serviceProviderIds.stream()
				.map(serviceProviderDto -> serviceProviderRepo.findById(serviceProviderDto).orElseThrow(
						() -> new ResourceNotFoundException("ServiceProvider", "id", serviceProviderDto)))
				.collect(Collectors.toSet());
		List<Job> jobList = jobRepo.findByPotentialServiceProvidersIn(potentialServiceProviders);
		Type listType = new TypeToken<List<JobDto>>() {}.getType();
		return modelMapper.map(jobList, listType);
	}

	private Job setFeilds(JobDto jobDto) {
		Job job;
		Client client;
		ServiceProvider serviceProvider;
		Moderator moderator = new Moderator();
		
		job = modelMapper.map(jobDto, Job.class);
		client = clientRepo.findById(jobDto.getClient().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Client", "id", jobDto.getClient().getId()));
		job.setClient(client);
		
		if (jobDto.getModerator() != null) {
			moderator = moderatorRepo.findById(jobDto.getModerator().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Moderator", "id", jobDto.getModerator().getId()));
			job.setModerator(moderator);
		}
		
		if (jobDto.getServiceProvider() != null) {
			serviceProvider = serviceProviderRepo.findById(jobDto.getServiceProvider().getId()).orElseThrow(
					() -> new ResourceNotFoundException("ServiceProvider", "id", jobDto.getServiceProvider().getId()));
			job.setServiceProvider(serviceProvider);
		}
		
		if (jobDto.getCreatedBy().getId() == client.getId()) {
			job.setCreatedBy((User)client);
		} else if (jobDto.getCreatedBy().getId() == moderator.getId()) {
			job.setCreatedBy((User)moderator);
		}
		
		if (!jobDto.getRequiredSkills().isEmpty()) {
			Set<Skill> requiredSkills = jobDto.getRequiredSkills().stream()
					.map(skillDto -> skillRepo.findById(skillDto.getId())
							.orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillDto.getId())))
					.collect(Collectors.toSet());
			job.setRequiredSkills(requiredSkills);
		}

		if (!jobDto.getPotentialServiceProviders().isEmpty()) {
			Set<ServiceProvider> potentialServiceProviders = jobDto.getPotentialServiceProviders().stream()
					.map(serviceProviderDto -> serviceProviderRepo.findById(serviceProviderDto.getId()).orElseThrow(
							() -> new ResourceNotFoundException("ServiceProvider", "id", serviceProviderDto.getId())))
					.collect(Collectors.toSet());
			job.setPotentialServiceProviders(potentialServiceProviders);
		}
		
		return job;
	}
}
