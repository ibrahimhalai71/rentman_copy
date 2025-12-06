
package com.rentmen.app.entities;

import com.rentmen.app.utils.StringListConverter;
import com.rentmen.app.utils.StringMapConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(
		name = "archived_jobs"
)
public class ArchiveJob {
	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(
			name = "client_id"
	)
	private Client client;
	@ManyToOne
	@JoinColumn(
			name = "moderator_id"
	)
	private Moderator moderator;
	@ManyToOne
	@JoinColumn(
			name = "created_by"
	)
	private User createdBy;
	@ManyToMany
	@JoinTable(
			name = "archive_job_service_provider_ids",
			joinColumns = {@JoinColumn(
					name = "archive_job_id",
					referencedColumnName = "id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "service_provider_id",
					referencedColumnName = "id"
			)}
	)
	private List<ServiceProvider> serviceProvidersList = new ArrayList();
	@ManyToMany(
			fetch = FetchType.EAGER
	)
	@JoinTable(
			name = "archive_job_required_skills",
			joinColumns = {@JoinColumn(
					name = "archive_job_id",
					referencedColumnName = "id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "skill_id",
					referencedColumnName = "id"
			)}
	)
	Set<Skill> requiredSkills = new HashSet();
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate creationDate;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate startDate;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate endDate;
	private String title;
	private String destination;
	private String role;
	@Column(
			name = "description",
			columnDefinition = "LONGTEXT"
	)
	private String description;
	private String status;
	private Integer moderatorApproval;
	private LocalTime startTime;
	private LocalTime endTime;
	private Integer numberOfPeople;
	private String projectLeadName;
	private String projectLeadNumber;
	@Convert(
			converter = StringListConverter.class
	)
	@Column(
			name = "job_other_skills",
			nullable = true
	)
	private List<String> jobOtherSkills;
	@Convert(
			converter = StringMapConverter.class
	)
	@Column(
			name = "reviewed_by_ServiceProvider",
			nullable = true
	)
	private Map<Long, Boolean> reviewedByServiceProvider;
	private Boolean reviewedByClient;
	private Boolean active;

	public ArchiveJob() {
	}

	public ArchiveJob(Long id, Client client, Moderator moderator, User createdBy, List<ServiceProvider> serviceProvidersList, Set<Skill> requiredSkills, LocalDate creationDate, LocalDate startDate, LocalDate endDate, List<PotentialJobOffer> potentialJobOffers, String title, String destination, String role, String description, String status, Integer moderatorApproval, LocalTime startTime, LocalTime endTime, Integer numberOfPeople, String projectLeadName, String projectLeadNumber, List<String> jobOtherSkills, Map<Long, Boolean> reviewedByServiceProvider, Boolean reviewedByClient, Boolean active) {
		this.id = id;
		this.client = client;
		this.moderator = moderator;
		this.createdBy = createdBy;
		this.serviceProvidersList = serviceProvidersList;
		this.requiredSkills = requiredSkills;
		this.creationDate = creationDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.destination = destination;
		this.role = role;
		this.description = description;
		this.status = status;
		this.moderatorApproval = moderatorApproval;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numberOfPeople = numberOfPeople;
		this.projectLeadName = projectLeadName;
		this.projectLeadNumber = projectLeadNumber;
		this.jobOtherSkills = jobOtherSkills;
		this.reviewedByServiceProvider = reviewedByServiceProvider;
		this.reviewedByClient = reviewedByClient;
		this.active = active;
	}

	public ArchiveJob(Job job) {
		this.id = job.getId();
		this.client = job.getClient();
		this.moderator = job.getModerator();
		this.createdBy = job.getCreatedBy();
		this.serviceProvidersList = job.getServiceProvidersList();
		this.requiredSkills = job.getRequiredSkills();
		this.creationDate = job.getCreationDate();
		this.startDate = job.getStartDate();
		this.endDate = job.getEndDate();
		this.title = job.getTitle();
		this.destination = job.getDestination();
		this.role = job.getRole();
		this.description = job.getDescription();
		this.status = job.getStatus().name();
		this.moderatorApproval = job.getModeratorApproval();
		this.startTime = job.getStartTime();
		this.endTime = job.getEndTime();
		this.numberOfPeople = job.getNumberOfPeople();
		this.projectLeadName = job.getProjectLeadName();
		this.projectLeadNumber = job.getProjectLeadNumber();
		this.jobOtherSkills = job.getJobOtherSkills();
		this.reviewedByServiceProvider = job.getReviewedByServiceProvider();
		this.reviewedByClient = job.getReviewedByClient();
		this.active = job.getActive();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Moderator getModerator() {
		return this.moderator;
	}

	public void setModerator(Moderator moderator) {
		this.moderator = moderator;
	}

	public User getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public List<ServiceProvider> getServiceProvidersList() {
		return this.serviceProvidersList;
	}

	public void setServiceProvidersList(List<ServiceProvider> serviceProvidersList) {
		this.serviceProvidersList = serviceProvidersList;
	}

	public Set<Skill> getRequiredSkills() {
		return this.requiredSkills;
	}

	public void setRequiredSkills(Set<Skill> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getModeratorApproval() {
		return this.moderatorApproval;
	}

	public void setModeratorApproval(Integer moderatorApproval) {
		this.moderatorApproval = moderatorApproval;
	}

	public LocalTime getStartTime() {
		return this.startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return this.endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Integer getNumberOfPeople() {
		return this.numberOfPeople;
	}

	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public String getProjectLeadName() {
		return this.projectLeadName;
	}

	public void setProjectLeadName(String projectLeadName) {
		this.projectLeadName = projectLeadName;
	}

	public String getProjectLeadNumber() {
		return this.projectLeadNumber;
	}

	public void setProjectLeadNumber(String projectLeadNumber) {
		this.projectLeadNumber = projectLeadNumber;
	}

	public List<String> getJobOtherSkills() {
		return this.jobOtherSkills;
	}

	public void setJobOtherSkills(List<String> jobOtherSkills) {
		this.jobOtherSkills = jobOtherSkills;
	}

	public Map<Long, Boolean> getReviewedByServiceProvider() {
		return this.reviewedByServiceProvider;
	}

	public void setReviewedByServiceProvider(Map<Long, Boolean> reviewedByServiceProvider) {
		this.reviewedByServiceProvider = reviewedByServiceProvider;
	}

	public Boolean getReviewedByClient() {
		return this.reviewedByClient;
	}

	public void setReviewedByClient(Boolean reviewedByClient) {
		this.reviewedByClient = reviewedByClient;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
