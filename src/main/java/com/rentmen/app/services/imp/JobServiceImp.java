package com.rentmen.app.services.imp;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.TypeMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentmen.app.Constants.Constants;
import com.rentmen.app.DTO.InvoiceDto;
import com.rentmen.app.DTO.JobDto;
import com.rentmen.app.DTO.PotentialJobOfferDto;
import com.rentmen.app.DTO.ReviewFormClient;
import com.rentmen.app.DTO.UserDto;
import com.rentmen.app.configurations.JwtTokenHelper;
import com.rentmen.app.entities.Client;
import com.rentmen.app.entities.Invoice;
import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.Moderator;
import com.rentmen.app.entities.PotentialJobOffer;
import com.rentmen.app.entities.PotentialJobOfferId;
import com.rentmen.app.entities.ServiceProvider;
import com.rentmen.app.entities.Skill;
import com.rentmen.app.entities.User;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.ClientRepo;
import com.rentmen.app.repositories.InvoiceRepo;
import com.rentmen.app.repositories.JobRepo;
import com.rentmen.app.repositories.ModeratorRepo;
import com.rentmen.app.repositories.PotentialJobOfferRepo;
import com.rentmen.app.repositories.ServiceProviderRepo;
import com.rentmen.app.repositories.SkillRepo;
import com.rentmen.app.repositories.UserRepo;
import com.rentmen.app.services.JobService;
import com.rentmen.app.utils.Billable;
import com.rentmen.app.utils.StringMapConverter;
import com.rentmen.app.utils.UtilFunctions;

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
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private InvoiceRepo invoiceRepo;

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
		
		if(!jobDto.getPotentialJobOffers().isEmpty()) {
			List<PotentialJobOffer> pjos = new ArrayList<PotentialJobOffer>();
			if (jobDto.getId() == null) {
				pjos = jobDto.getPotentialJobOffers().stream().map(dto -> {
					ServiceProvider sp = serviceProviderRepo.findById(dto.getServiceProviderId()).orElseThrow(
							() -> new ResourceNotFoundException("ServiceProvider", "id", dto.getServiceProviderId()));
					return new PotentialJobOffer(job, sp);
				}).collect(Collectors.toList());
			} else {
//				List<PotentialJobOfferId> pjoIds = jobDto.getPotentialJobOffers().stream()
//						.filter(pjo -> jobDto.getId() != null)
//						.map(pjo -> new PotentialJobOfferId(jobDto.getId(), pjo.getServiceProviderId()))
//						.collect(Collectors.toList());
//				pjos = pjoRepo.findAllById(pjoIds);
				
//				HashMap<Long,PotentialJobOffer> mapSpToPjos = new HashMap<Long,PotentialJobOffer>();
//				for(PotentialJobOffer pjo : pjos) {
//					mapSpToPjos.put(pjo.getServiceProvider().getId(), pjo);
//				}
//				for(PotentialJobOfferDto pjoDto : jobDto.getPotentialJobOffers()) {
//					if(!mapSpToPjos.containsKey(pjoDto.getServiceProviderId())) {
//						ServiceProvider sp = serviceProviderRepo.findById(pjoDto.getServiceProviderId()).orElseThrow(
//								() -> new ResourceNotFoundException("ServiceProvider", "id", pjoDto.getServiceProviderId()));
//						PotentialJobOffer pjo = new PotentialJobOffer(job, sp);
//						pjos.add(pjo);
//					}
//				}
				
				for(PotentialJobOfferDto pjoDto : jobDto.getPotentialJobOffers()) {
					PotentialJobOfferId pjoId= new PotentialJobOfferId(jobDto.getId(), pjoDto.getServiceProviderId());
					Optional<PotentialJobOffer> pjo = pjoRepo.findById(pjoId);
					if(pjo.isPresent()) {
						pjos.add(pjo.get());
					}else {
						ServiceProvider sp = serviceProviderRepo.findById(pjoDto.getServiceProviderId()).orElseThrow(
								() -> new ResourceNotFoundException("ServiceProvider", "id", pjoDto.getServiceProviderId()));
						PotentialJobOffer newPjo = new PotentialJobOffer(job, sp);
						pjos.add(newPjo);
					}
				}
			}
			job.setPotentialJobOffers(pjos);
		}
		
		return job;
	}
	
	
	@Override
	public JobDto getClientReviewForm(String token) throws Exception {
		if (jwtTokenHelper.validateTokenForReviewForm(token)) {
			Long jobId = jwtTokenHelper.getJobIdFromToken(token);
			return modelMapper.map(
					this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId)),
					JobDto.class);
		} else {
			throw new Exception("Error: invalid token or token expired");

		}
	}
	@Transactional
	@Override
	public void postClientReviewForm(String token, List<ReviewFormClient> reviews) throws Exception{
		if (jwtTokenHelper.validateTokenForReviewForm(token)) {
			Long jobId = jwtTokenHelper.getJobIdFromToken(token);
			Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
			if(job.getReviewedByClient() != null ? job.getReviewedByClient() : false) {
				throw new Exception("Error: review has been done");
			}
			List<ServiceProvider> spList = job.getServiceProvidersList();
			Map<Long, Float> clientReviewMap = reviews.stream()
					.collect(Collectors.toMap(ReviewFormClient::getServiceProviderId, ReviewFormClient::getRating));
			for(ServiceProvider sp :spList) {
				Float review = clientReviewMap.get(sp.getId());
				Float rating = sp.getRating();
				Integer totalJobs = jobRepo.getReviewedJobsCount(sp.getId());
				if (review > 0 || rating > 0) {
					rating = ((rating * totalJobs) + review) / (totalJobs + 1);
					sp.setRating(rating);
					serviceProviderRepo.updateRating(rating, sp.getId());
				}
			}
			jobRepo.setReviewedByClientTrue(jobId);
		} else {
			throw new Exception("Error: invalid token or token expired");

		}
	}
	
	@Override
	public Boolean isReviewedServiceProviderReviewForm(String token, Long serviceProviderId) throws Exception  {
		if (jwtTokenHelper.validateTokenForReviewForm(token)) {
			Long jobId = jwtTokenHelper.getJobIdFromToken(token);
			Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
			Map<Long, Boolean> spReviewMap = job.getReviewedByServiceProvider();
			List<ServiceProvider> spList = job.getServiceProvidersList();
			if(!spList.stream().anyMatch(sp -> sp.getId() == serviceProviderId)) {
				throw new Exception("Error: Service-provider/student not present in job");
			}
			if(spReviewMap.containsKey(serviceProviderId)) {
				return spReviewMap.get(serviceProviderId);
			}else {
				return false;
			}
		} else {
			throw new Exception("Error: invalid token or token expired");

		}
	}
	
	@Override
	public void postReviewFormServiceProvider(String token, InvoiceDto invoiceDto) throws Exception {
		Long serviceProviderId = invoiceDto.getServiceProvider().getId();
		if(!isReviewedServiceProviderReviewForm(token, serviceProviderId)){
			Long jobId = jwtTokenHelper.getJobIdFromToken(token);
			Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
			Map<Long, Boolean> spReviewMap = job.getReviewedByServiceProvider();
			if(!spReviewMap.containsKey(serviceProviderId) || !spReviewMap.get(serviceProviderId)) {
				spReviewMap.put(serviceProviderId, true);
				String strSPReviewMap = UtilFunctions.convertWithStream(spReviewMap);
				this.jobRepo.setReviewedByServiceProviderTrue(strSPReviewMap, jobId);
				Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
				invoice.setJob(job);
				invoice.setServiceProvider(this.serviceProviderRepo.findById(serviceProviderId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId)));
				this.invoiceRepo.save(invoice);
			}
		}else {
			throw new Exception("Error: review form already filled");
		}
	}
	
	public String getInvoice(Long jobId) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Optional<List<Invoice>> invoiceList = this.invoiceRepo.findByJobId(jobId);
		Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
		Double extraCost = 0.0;
		Double kilometer = 0.0;
		if (invoiceList.isPresent()) {
			extraCost = invoiceList.get().stream().mapToDouble(Invoice::getExtraCost).sum();
			kilometer = invoiceList.get().stream().mapToDouble(Invoice::getKilometer).sum();
		}
		Double cost = getJobCost(job);
		Double totalCost = cost + extraCost + (kilometer * job.getClient().getKilometerRate());
		Double totalCostTax = totalCost * (Constants.VAT + 1);
		returnMap.put("extra_cost", extraCost);
		returnMap.put("kilometer", kilometer);
		returnMap.put("job_cost", cost);
		returnMap.put("total_cost", totalCost);
		returnMap.put("total_cost_tax", totalCostTax);
		String returnString = objectMapper.writeValueAsString(returnMap);
		return returnString;
	}
	
	public Double getJobCost(Job job) {
		Double cost = 0.0;
		Double clientRate = job.getClient().getDiscussedRate();
		Long days = ChronoUnit.DAYS.between(job.getStartDate(), job.getEndDate());
		Long hours = ChronoUnit.HOURS.between(job.getStartTime(), job.getEndTime());
		if (job.getClient().getBillable() == Billable.DAY) {
			cost = days * clientRate;
		} else {
			cost = (days * hours) * clientRate;
		}
		return cost;
	}
 }
