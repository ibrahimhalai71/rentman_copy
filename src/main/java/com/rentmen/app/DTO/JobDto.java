
package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.entities.Projects;
import com.rentmen.app.utils.JobStatus;
import com.rentmen.app.utils.UtilFunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class JobDto {
    private Long id;
    @JsonProperty("client")
    private UserDto client;
    @JsonProperty("moderator")
    private UserDto moderator;
    @JsonProperty("service_providers_list")
    private List<UserDto> serviceProvidersList = new ArrayList();
    @JsonProperty("created_by")
    private UserDto createdBy;
    @JsonProperty("required_skills")
    private Set<SkillDto> requiredSkills = new HashSet();
    @JsonProperty("potential_service_providers")
    private Set<PotentialJobOfferDto> potentialJobOffers = new HashSet();
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
    @JsonProperty("project")
    private ProjectsDto project;
    @JsonProperty("job_other_skills")
    private List<String> jobOtherSkills;
    @JsonProperty("is_expired")
    private Boolean isExpired;
    @JsonProperty("invoice_number")
    private Long invoiceNumber;

    public JobDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getClient() {
        return this.client;
    }

    public void setClient(UserDto client) {
        this.client = client;
    }

    public UserDto getModerator() {
        return this.moderator;
    }

    public void setModerator(UserDto moderator) {
        this.moderator = moderator;
    }

    public UserDto getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy;
    }

    public Set<SkillDto> getRequiredSkills() {
        return this.requiredSkills;
    }

    public void setRequiredSkills(Set<SkillDto> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public Set<PotentialJobOfferDto> getPotentialJobOffers() {
        return this.potentialJobOffers;
    }

    public void setPotentialJobOffers(Set<PotentialJobOfferDto> potentialJobOffers) {
        this.potentialJobOffers = potentialJobOffers;
    }


    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
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

    public List<UserDto> getServiceProvidersList() {
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

    public ProjectsDto getProject() {
        return project;
    }

    public void setProject(ProjectsDto project) {
        this.project = project;
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

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public Map<Long, Boolean> getReviewedByServiceProvider() {
        return this.reviewedByServiceProvider;
    }

    public void setReviewedByServiceProvider(Map<Long, Boolean> reviewedByServiceProvider) {
        this.reviewedByServiceProvider = reviewedByServiceProvider;
    }

    public void setReviewedByServiceProvider(String strReviewBySP) {
        this.reviewedByServiceProvider = strReviewBySP != null ? UtilFunctions.convertWithStream(strReviewBySP) : null;
    }
}
