package com.rentmen.app.services.imp;

import com.rentmen.app.Constants.Constants;
import com.rentmen.app.DTO.*;
import com.rentmen.app.configurations.JwtTokenHelper;
import com.rentmen.app.entities.*;
import com.rentmen.app.entities.User.RateType;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.*;
import com.rentmen.app.services.JobService;
import com.rentmen.app.utils.UtilFunctions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
		Job job = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
		return modelMapper.map(job, JobDto.class);
	}

	@Override
	public List<JobDto> getAllJobs() {
		List<Job> jobList = jobRepo.findAll();
		Type listType = new TypeToken<List<JobDto>>() {
		}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	@Transactional
	public Boolean deleteJob(Long id) {
		try {
			Job job = jobRepo.findById(id).get();
			invoiceRepo.deleteByJob(job);
			jobRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<JobDto> getAllJobsByClientId(Long id) {
		List<Job> jobList = jobRepo.findByClientId(id);
		Type listType = new TypeToken<List<JobDto>>() {
		}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public List<JobDto> getAllJobsByModeratorId(Long id) {
		List<Job> jobList = jobRepo.findByModeratorId(id);
		Type listType = new TypeToken<List<JobDto>>() {
		}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public List<JobDto> getAllJobsServiceProviderIds(List<Long> serviceProviderIds) {
		List<ServiceProvider> serviceProviders = serviceProviderIds.stream()
				.map(serviceProviderDto -> serviceProviderRepo.findById(serviceProviderDto)
						.orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", serviceProviderDto)))
				.collect(Collectors.toList());
		List<Job> jobList = jobRepo.findByServiceProvidersListIn(serviceProviders);
		Type listType = new TypeToken<List<JobDto>>() {
		}.getType();
		return modelMapper.map(jobList, listType);
	}

	@Override
	public List<JobDto> getAllJobsCreatedById(Long id) {
		List<Job> jobList = jobRepo.findByCreatedBy(id);
		Type listType = new TypeToken<List<JobDto>>() {
		}.getType();
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
	public List<PotentialJobOfferDto> getPotentialJobOffersToServiceProvider(Long serviceProviderId) {
		List<PotentialJobOffer> pjos = pjoRepo.findbyServiceProviderId(serviceProviderId);
		Type listType = new TypeToken<List<PotentialJobOfferDto>>() {
		}.getType();
		return modelMapper.map(pjos, listType);
	}

	@Transactional
	@Override
	public int acceptJobOffers(List<Long> jobIds, Long serviceProviderId) {
		for (Long jobId : jobIds) {
			jobRepo.addToServiceProvidersList(jobId, serviceProviderId);
		}
		return pjoRepo.acceptJobOffers(jobIds, serviceProviderId);
	}

	@Override
	public int rejectJobOffers(List<Long> jobIds, Long serviceProviderId) {
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
			job.setCreatedBy((User) client);
		} else if (jobDto.getCreatedBy().getId() == moderator.getId()) {
			job.setCreatedBy((User) moderator);
		}

		if (!jobDto.getRequiredSkills().isEmpty()) {
			Set<Skill> requiredSkills = jobDto.getRequiredSkills().stream()
					.map(skillDto -> skillRepo.findById(skillDto.getId())
							.orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillDto.getId())))
					.collect(Collectors.toSet());
			job.setRequiredSkills(requiredSkills);
		}

		if (!jobDto.getPotentialJobOffers().isEmpty()) {
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

				for (PotentialJobOfferDto pjoDto : jobDto.getPotentialJobOffers()) {
					PotentialJobOfferId pjoId = new PotentialJobOfferId(jobDto.getId(), pjoDto.getServiceProviderId());
					Optional<PotentialJobOffer> pjo = pjoRepo.findById(pjoId);
					if (pjo.isPresent()) {
						pjos.add(pjo.get());
					} else {
						ServiceProvider sp = serviceProviderRepo.findById(pjoDto.getServiceProviderId())
								.orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id",
										pjoDto.getServiceProviderId()));
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
	public void postClientReviewForm(String token, List<ReviewFormClient> reviews) throws Exception {
		if (jwtTokenHelper.validateTokenForReviewForm(token)) {
			Long jobId = jwtTokenHelper.getJobIdFromToken(token);
			Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
			if (job.getReviewedByClient() != null ? job.getReviewedByClient() : false) {
				throw new Exception("Error: review has been done");
			}
			List<ServiceProvider> spList = job.getServiceProvidersList();
			Map<Long, Float> clientReviewMap = reviews.stream()
					.collect(Collectors.toMap(ReviewFormClient::getServiceProviderId, ReviewFormClient::getRating));
			for (ServiceProvider sp : spList) {
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
	public Boolean isReviewedServiceProviderReviewForm(String token, Long serviceProviderId) throws Exception {
		if (jwtTokenHelper.validateTokenForReviewForm(token)) {
			Long jobId = jwtTokenHelper.getJobIdFromToken(token);
			Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
			Map<Long, Boolean> spReviewMap = job.getReviewedByServiceProvider();
			List<ServiceProvider> spList = job.getServiceProvidersList();
			if (!spList.stream().anyMatch(sp -> sp.getId() == serviceProviderId)) {
				throw new Exception("Error: Service-provider/student not present in job");
			}
			if (spReviewMap != null) {
				if (spReviewMap.containsKey(serviceProviderId)) {

					return spReviewMap.get(serviceProviderId);
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			throw new Exception("Error: invalid token or token expired");

		}
	}

	@Override
	public void postReviewFormServiceProvider(String token, InvoiceDto invoiceDto) throws Exception {
		Long serviceProviderId = invoiceDto.getServiceProvider().getId();
		if (!isReviewedServiceProviderReviewForm(token, serviceProviderId)) {
			Long jobId = jwtTokenHelper.getJobIdFromToken(token);
			Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
			Map<Long, Boolean> spReviewMap = job.getReviewedByServiceProvider();
			spReviewMap = new HashMap<>();
			if (!spReviewMap.containsKey(serviceProviderId) || !spReviewMap.get(serviceProviderId)) {
				spReviewMap.put(serviceProviderId, true);
				String strSPReviewMap = UtilFunctions.convertWithStream(spReviewMap);
				this.jobRepo.setReviewedByServiceProviderTrue(strSPReviewMap, jobId);
				Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
				invoice.setJob(job);
				invoice.setServiceProvider(this.serviceProviderRepo.findById(serviceProviderId)
						.orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId)));
				this.invoiceRepo.save(invoice);
			}
		} else {
			throw new Exception("Error: review form already filled");
		}
	}

	public GeneratedInvoiceDto getInvoice(Long jobId) throws Exception {
		Optional<List<Invoice>> invoiceList = this.invoiceRepo.findByJobId(jobId);
		Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
		Double extraCost = 0.0;
		Double kilometer = 0.0;
		if (invoiceList.isPresent()) {
			extraCost = invoiceList.get().stream().mapToDouble(Invoice::getExtraCost).sum();
			kilometer = invoiceList.get().stream().mapToDouble(Invoice::getKilometer).sum();
			
			invoiceList.get().stream().peek(invoice -> {
			    boolean reviewedBySP = job.getReviewedByServiceProvider()
			                               .getOrDefault(invoice.getServiceProvider().getId(), false);
			    if (reviewedBySP) {
			        invoice.setReviewedBySP(true);
			    }
			    
			    if (job.getReviewedByClient()) {
			        invoice.setReviewedByClient(true);
			    }
			}).collect(Collectors.toList());
		}
		Double cost = getJobCost(job);
		Double kilometerRate = job.getClient().getKilometerRate() != null ? job.getClient().getKilometerRate() : 0.0;
		Double totalCost = cost + extraCost + (kilometer * kilometerRate);
		Double totalCostTax = totalCost * (Constants.VAT + 1);
		Type listType = new TypeToken<List<InvoiceDto>>() {
		}.getType();
		List<InvoiceDto> invoiceListDto = this.modelMapper.map(invoiceList, listType);
		GeneratedInvoiceDto generatedInvoiceDto = new GeneratedInvoiceDto(invoiceListDto, extraCost, kilometer, cost,
				totalCost, totalCostTax);
		return generatedInvoiceDto;
	}

	public Double getJobCost(Job job) {
		Double cost = 0.0;
		Double clientRate = job.getClient().getDiscussedRate() != null ? job.getClient().getDiscussedRate() : 0.0;
		Long days = ChronoUnit.DAYS.between(job.getStartDate(), job.getEndDate());
		Long hours = ChronoUnit.HOURS.between(job.getStartTime(), job.getEndTime());
		LocalTime nightStart = LocalTime.of(18, 0);
        LocalTime nightEnd = LocalTime.of(6, 0);
        DayOfWeek dayOfWeek = job.getStartDate().getDayOfWeek();
        ;
		if (job.getClient().getRateType() == RateType.DAY) {
			cost = days * clientRate;
		} else {
			if (job.getClient().getDayRate() != null && job.getStartTime().isAfter(nightEnd) && job.getStartTime().isBefore(nightStart)) 
				cost = (days * hours) * job.getClient().getDayRate();
			else if (job.getClient().getNightRate() != null)
				cost = (days * hours) * job.getClient().getNightRate();
			
			
			if (job.getClient().getWeekendRate() != null && (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY))
				cost = (days * hours) * job.getClient().getWeekendRate();
			else if(job.getClient().getDayRate() == null && job.getClient().getNightRate() == null && job.getClient().getWeekendRate() == null)
				cost = (days * hours) * clientRate;
		}
		return cost;
	}
}
