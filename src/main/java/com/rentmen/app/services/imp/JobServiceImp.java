package com.rentmen.app.services.imp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentmen.app.DTO.JobDto;
import com.rentmen.app.DTO.PotentialJobOfferDto;
import com.rentmen.app.DTO.UserDto;
import com.rentmen.app.entities.Client;
import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.Moderator;
import com.rentmen.app.entities.PotentialJobOffer;
import com.rentmen.app.entities.PotentialJobOfferId;
import com.rentmen.app.entities.ServiceProvider;
import com.rentmen.app.entities.Skill;
import com.rentmen.app.entities.User;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.ClientRepo;
import com.rentmen.app.repositories.JobRepo;
import com.rentmen.app.repositories.ModeratorRepo;
import com.rentmen.app.repositories.PotentialJobOfferRepo;
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
	
	@Autowired
	private PotentialJobOfferRepo pjoRepo;

	@Override
	public JobDto createJob(JobDto jobDto) {
		Job job = setFeilds(jobDto);
		job = jobRepo.save(job);
		return modelMapper.map(job, JobDto.class);
	}

	@Override
	public JobDto updateJob(JobDto jobDto) {
//		Job oldJob= jobRepo.findById(jobDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobDto.getId()));
		Job updatedJob = setFeilds(jobDto);
//		updatedJob.setId(oldJob.getId());
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
	public List<JobDto> getAllJobsServiceProviderIds(List<Long> serviceProviderIds) {
		List<ServiceProvider> serviceProviders = serviceProviderIds.stream()
				.map(serviceProviderDto -> serviceProviderRepo.findById(serviceProviderDto).orElseThrow(
						() -> new ResourceNotFoundException("ServiceProvider", "id", serviceProviderDto)))
				.collect(Collectors.toList());
		List<Job> jobList = jobRepo.findByServiceProvidersListIn(serviceProviders);
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
		Set<PotentialJobOffer> potentialServiceProviders = serviceProviderIds.stream().map(spId -> {
			ServiceProvider sp = serviceProviderRepo.findById(spId)
					.orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", spId));
			PotentialJobOffer pjo = new PotentialJobOffer();
			return pjo;
		}).collect(Collectors.toSet());
		List<Job> jobList = jobRepo.findByPotentialJobOffersIn(potentialServiceProviders);
//		Type listType = new TypeToken<List<JobDto>>() {}.getType();
//		return modelMapper.map(jobList, listType);
		return null;
	}

	@Override
	public List<PotentialJobOfferDto> getPotentialJobOffersToServiceProvider(Long serviceProviderId){
		List<PotentialJobOffer> pjos = pjoRepo.findbyServiceProviderId(serviceProviderId);
		Type listType = new TypeToken<List<PotentialJobOfferDto>>() {}.getType();
		return modelMapper.map(pjos, listType);
	}
	
	@Transactional
	@Override
	public int acceptJobOffers(List<Long> jobIds, Long serviceProviderId){
		for(Long jobId: jobIds) {
			jobRepo.addToServiceProvidersList(jobId,serviceProviderId);
		}
		return pjoRepo.acceptJobOffers(jobIds, serviceProviderId);
	}
	@Override
	public int rejectJobOffers(List<Long> jobIds, Long serviceProviderId){
		return pjoRepo.rejectJobOffers(jobIds, serviceProviderId);
	}
	
	private Job setFeilds(JobDto jobDto) {
		Job job;
		Client client;
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
		
		if (jobDto.getServiceProvidersList() != null && !jobDto.getServiceProvidersList().isEmpty()) {
			List<ServiceProvider> serviceProviderList = jobDto.getServiceProvidersList().stream()
					.map(sp -> serviceProviderRepo.findById(sp.getId())
							.orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", sp.getId())))
					.collect(Collectors.toList());

			job.setServiceProvidersList(serviceProviderList);
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
		
		if (!jobDto.getPotentialJobOffers().isEmpty()) {
			List<PotentialJobOffer> pjos;
			pjos = jobDto.getPotentialJobOffers().stream().map(dto -> {
				ServiceProvider sp = serviceProviderRepo.findById(dto.getServiceProviderId()).orElseThrow(
						() -> new ResourceNotFoundException("ServiceProvider", "id", dto.getServiceProviderId()));
				return new PotentialJobOffer(job, sp);
			}).collect(Collectors.toList());
			job.setPotentialJobOffers(pjos);
		}
		
		return job;
	}
}
