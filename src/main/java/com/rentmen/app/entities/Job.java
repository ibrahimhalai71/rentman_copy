package com.rentmen.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rentmen.app.utils.JobStatus;
import com.rentmen.app.utils.StringListConverter;
import com.rentmen.app.utils.StringMapConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(
		name = "job"
)
public class Job {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	private Long id;
	@ManyToOne
	@JoinColumn(
			name = "client_id"
	)
	@JsonBackReference
	private Client client;
	@ManyToOne
	@JoinColumn(
			name = "moderator_id"
	)
	private Moderator moderator;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "job_service_provider_ids",
			joinColumns = {@JoinColumn(
					name = "job_id",
					referencedColumnName = "id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "service_provider_id",
					referencedColumnName = "id"
			)}
	)
	private List<ServiceProvider> serviceProvidersList = new ArrayList();
	@ManyToOne
	@JoinColumn(
			name = "created_by"
	)
	private User createdBy;
	@ManyToMany(
			fetch = FetchType.EAGER
	)
	@JoinTable(
			name = "job_required_skills",
			joinColumns = {@JoinColumn(
					name = "job_id",
					referencedColumnName = "id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "skill_id",
					referencedColumnName = "id"
			)}
	)
	Set<Skill> requiredSkills = new HashSet();
	@OneToMany(
			mappedBy = "job",
			cascade = {CascadeType.ALL},
			fetch = FetchType.LAZY,
			orphanRemoval = true
	)
	@JsonManagedReference
	private List<PotentialJobOffer> potentialJobOffers;
	private Boolean active;
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
	@Enumerated(EnumType.STRING)
	private JobStatus status;
	@Column(
			name = "moderator_approval"
	)
	private Integer moderatorApproval;
	@Column(
			name = "start_time"
	)
	private LocalTime startTime;
	@Column(
			name = "end_time"
	)
	private LocalTime endTime;
	@Column(
			name = "number_of_people"
	)
	private Integer numberOfPeople;
	@Column(
			name = "project_lead_name"
	)
	private String projectLeadName;
	@Column(
			name = "project_lead_number"
	)
	private String projectLeadNumber;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Projects project;

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
	@Column(name = "invoice_number")
	private Long invoiceNumber;

	private Boolean isExpired;


	public Job() {
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

	public Set<Skill> getRequiredSkills() {
		return this.requiredSkills;
	}

	public void setRequiredSkills(Set<Skill> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public List<PotentialJobOffer> getPotentialJobOffers() {
		return this.potentialJobOffers;
	}

	public void setPotentialJobOffers(List<PotentialJobOffer> potentialJobOffers) {
		this.potentialJobOffers = potentialJobOffers;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Boolean getExpired() {
		return isExpired;
	}

	public void setExpired(Boolean expired) {
		isExpired = expired;
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

	public JobStatus getStatus() {
		return this.status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public List<ServiceProvider> getServiceProvidersList() {
		return this.serviceProvidersList;
	}

	public Integer getModeratorApproval() {
		return this.moderatorApproval;
	}

	public LocalTime getStartTime() {
		return this.startTime;
	}

	public LocalTime getEndTime() {
		return this.endTime;
	}

	public Integer getNumberOfPeople() {
		return this.numberOfPeople;
	}

	public String getProjectLeadName() {
		return this.projectLeadName;
	}

	public String getProjectLeadNumber() {
		return this.projectLeadNumber;
	}

	public void setServiceProvidersList(List<ServiceProvider> serviceProvidersList) {
		this.serviceProvidersList = serviceProvidersList;
	}

	public void setModeratorApproval(Integer moderatorApproval) {
		this.moderatorApproval = moderatorApproval;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public void setProjectLeadName(String projectLeadName) {
		this.projectLeadName = projectLeadName;
	}

	public void setProjectLeadNumber(String projectLeadNumber) {
		this.projectLeadNumber = projectLeadNumber;
	}

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}

	public List<String> getJobOtherSkills() {
		return this.jobOtherSkills;
	}

	public void setJobOtherSkills(List<String> jobOtherSkills) {
		this.jobOtherSkills = jobOtherSkills;
	}

	public Boolean getReviewedByClient() {
		return this.reviewedByClient;
	}

	public void setReviewedByClient(Boolean reviewedByClient) {
		this.reviewedByClient = reviewedByClient;
	}

//	public Map<Long, Boolean> getReviewedByServiceProvider() {
//		return this.reviewedByServiceProvider;
//	}

	public Map<Long, Boolean> getReviewedByServiceProvider() {
		if (reviewedByServiceProvider == null) {
			reviewedByServiceProvider = new HashMap<>();
		}
		return reviewedByServiceProvider;
	}

	public void setReviewedByServiceProvider(Map<Long, Boolean> reviewedByServiceProvider) {
		this.reviewedByServiceProvider = reviewedByServiceProvider;
	}
}
