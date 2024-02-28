package com.rentmen.app.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.entities.PotentialJobOffer;
import com.rentmen.app.utils.JobStatus;

public class JobDto {
	private Long id;
	@JsonProperty("client")
	private UserDto client;
	
	@JsonProperty("moderator")
	private UserDto moderator;
	
	@JsonProperty("service_provider")
	private UserDto serviceProvider;
	
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
	private String payment;
	private String destination;
	private String role;
	private String description;
	private JobStatus status;
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
	public UserDto getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(UserDto serviceProvider) {
		this.serviceProvider = serviceProvider;
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
//	public Set<UserDto> getPotentialServiceProviders() {
//		return potentialServiceProviders;
//	}
//	public void setPotentialServiceProviders(Set<UserDto> potentialServiceProviders) {
//		this.potentialServiceProviders = potentialServiceProviders;
//	}
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
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
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
	
	

}
