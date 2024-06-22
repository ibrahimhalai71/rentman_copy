package com.rentmen.app.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.rentmen.app.utils.JobStatus;
import com.rentmen.app.utils.StringListConverter;
import com.rentmen.app.utils.StringMapConverter;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private Moderator moderator;

    @ManyToMany
    @JoinTable(
            name = "job_service_provider_ids",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_provider_id", referencedColumnName = "id")
        )
    private List<ServiceProvider> serviceProvidersList = new ArrayList<ServiceProvider>();
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_required_skills",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id")
        )
	Set<Skill> requiredSkills = new HashSet<>();
    
    @OneToMany(mappedBy = "job", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<PotentialJobOffer> potentialJobOffers;
    
    private Boolean active;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String title;
    private String destination;
    private String role;
    
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    
    @Column(name = "moderator_approval")
    private Integer moderatorApproval;
    
    @Column(name = "start_time")
    private LocalTime startTime;
    
    @Column(name = "end_time")
    private LocalTime endTime;
    
    @Column(name = "number_of_people")
    private Integer numberOfPeople;
    
    @Column(name = "project_lead_name")
    private String projectLeadName;
    
    @Column(name = "project_lead_number")
    private String projectLeadNumber;

    @Convert(converter = StringListConverter.class)
    @Column(name = "job_other_skills", nullable = true)
    private List<String> jobOtherSkills;
    // Other job-related fields and methods
    @Convert(converter = StringMapConverter.class)
    @Column(name = "reviewed_by_ServiceProvider", nullable = true)
    private Map<Long, Boolean> reviewedByServiceProvider;
    
    private Boolean reviewedByClient;
    // Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Moderator getModerator() {
		return moderator;
	}

	public void setModerator(Moderator moderator) {
		this.moderator = moderator;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Set<Skill> getRequiredSkills() {
		return requiredSkills;
	}

	public void setRequiredSkills(Set<Skill> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public List<PotentialJobOffer> getPotentialJobOffers() {
		return potentialJobOffers;
	}

	public void setPotentialJobOffers(List<PotentialJobOffer> potentialJobOffers) {
		this.potentialJobOffers = potentialJobOffers;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public List<ServiceProvider> getServiceProvidersList() {
		return serviceProvidersList;
	}

	public Integer getModeratorApproval() {
		return moderatorApproval;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}

	public String getProjectLeadName() {
		return projectLeadName;
	}

	public String getProjectLeadNumber() {
		return projectLeadNumber;
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

	public List<String> getJobOtherSkills() {
		return jobOtherSkills;
	}

	public void setJobOtherSkills(List<String> jobOtherSkills) {
		this.jobOtherSkills = jobOtherSkills;
	}

	public Boolean getReviewedByClient() {
		return reviewedByClient;
	}

	public void setReviewedByClient(Boolean reviewedByClient) {
		this.reviewedByClient = reviewedByClient;
	}

	public Map<Long, Boolean> getReviewedByServiceProvider() {
		return reviewedByServiceProvider;
	}

	public void setReviewedByServiceProvider(Map<Long, Boolean> reviewedByServiceProvider) {
		this.reviewedByServiceProvider = reviewedByServiceProvider;
	}
}

