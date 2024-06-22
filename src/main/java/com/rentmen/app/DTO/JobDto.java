package com.rentmen.app.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.entities.PotentialJobOffer;
import com.rentmen.app.entities.ServiceProvider;
import com.rentmen.app.utils.JobStatus;
import com.rentmen.app.utils.UtilFunctions;

public class JobDto {
	private Long id;
	@JsonProperty("client")
	private UserDto client;
	
	@JsonProperty("moderator")
	private UserDto moderator;
	
	@JsonProperty("service_providers_list")
	private List<UserDto> serviceProvidersList = new ArrayList<UserDto>();
	
	@JsonProperty("created_by")
	private UserDto createdBy;
	
	@JsonProperty("required_skills")
	private Set<SkillDto> requiredSkills = new HashSet<>();
	
	@JsonProperty("potential_service_providers")
	private Set<PotentialJobOfferDto> potentialJobOffers = new HashSet<>();
	//	private Set<UserDto> potentialServiceProviders = new HashSet<>();
	
	private Boolean active;
	
	@JsonProperty("creation_date")
	private LocalDate creationDate;
	
	@JsonProperty("start_date")
	private LocalDate startDate;
	
	@JsonProperty("end_date")
	private LocalDate endDate;
	
	private String title;
	private String destination;
	private String role;
	private String description;
	private JobStatus status;
	@JsonProperty("reviewed_by_client")
	private Boolean reviewedByClient;
	@JsonProperty("reviewed_by_service_provider")
    private Map<Long, Boolean> reviewedByServiceProvider;
	
	@JsonProperty("moderator_approval")
    private Integer moderatorApproval;
    
    @JsonProperty("start_time")
    private LocalTime startTime;
    
    @JsonProperty("end_time")
    private LocalTime endTime;
    
    @JsonProperty("number_of_people")
    private Integer numberOfPeople;
    
    @JsonProperty("project_lead_name")
    private String projectLeadName;
    
    @JsonProperty("project_lead_number")
    private String projectLeadNumber;
    
    @JsonProperty("job_other_skills")
    private List<String> jobOtherSkills;
    
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserDto getClient() {
		return client;
	}
	public void setClient(UserDto client) {
		this.client = client;
	}
	public UserDto getModerator() {
		return moderator;
	}
	public void setModerator(UserDto moderator) {
		this.moderator = moderator;
	}
	public UserDto getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserDto createdBy) {
		this.createdBy = createdBy;
	}
	public Set<SkillDto> getRequiredSkills() {
		return requiredSkills;
	}
	public void setRequiredSkills(Set<SkillDto> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}
	public Set<PotentialJobOfferDto> getPotentialJobOffers() {
		return potentialJobOffers;
	}
	public void setPotentialJobOffers(Set<PotentialJobOfferDto> potentialJobOffers) {
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
	public List<UserDto> getServiceProvidersList() {
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
	public void setServiceProvidersList(List<UserDto> serviceProvidersList) {
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
	public void setReviewedByServiceProvider(String strReviewBySP) {
		this.reviewedByServiceProvider = strReviewBySP != null ? UtilFunctions.convertWithStream(strReviewBySP) : null;
	}
}
