package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.utils.AvailabilityOptions;
import com.rentmen.app.utils.DriversLicense;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserDto {
	private long id;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("business_registration")
	private String businessRegistration;
	@NotEmpty
	@Size(min = 3, message = "Username must be at least 3 characters")
	private String email;

	private String VAT;
	@JsonProperty("discussed_rate")
	private Double discussedRate;
	@NotEmpty
	@Size(min = 4, max = 16, message = "Password should be between 4-16 characters")
	private String password;

	private String about;
	private Integer depId;
	private Boolean active;

	private Boolean status;
	@JsonProperty("profile_image")
	private String profileImage;
	
	//Client Attributes
	private String address;
	@JsonProperty("contact_person")
	private String contactPerson;
	@JsonProperty("phone_number")
	private String phoneNumber;
	@JsonProperty( "phone_number_planning")
	private String phoneNumberPlanning;
	@JsonProperty("invoice_mail")
	private String invoiceMail;
	@JsonProperty( "sector")
	private String sector;
	@JsonProperty( "kilometer_rate")
	private Double kilometerRate;
	
	//Moderator Attributes
	private String accessLevel;

	//Service Provider Attributes
	private Boolean availabilityStatus;	
	private Integer rating;
	private String contact;
	private Set<SkillDto> skills = new HashSet<>(); 
	@JsonProperty("availability_start_date")
	private LocalDate availabilityStartDate;
	@JsonProperty("availability_end_date")
	private LocalDate availabilityEndDate;
	@JsonProperty("availability_options")
	private AvailabilityOptions availabilityOptions;
	@JsonProperty("drivers_license")
	private DriversLicense driversLicense;
	private String agreement;
	@JsonProperty("date_of_birth")
	private LocalDate dateOfBirth;
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getDepId() {
		return this.depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Boolean getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(Boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Set<SkillDto> getSkills() {
		return skills;
	}

	public void setSkills(Set<SkillDto> skills) {
		this.skills = skills;
	}

	public LocalDate getAvailabilityStartDate() {
		return availabilityStartDate;
	}

	public LocalDate getAvailabilityEndDate() {
		return availabilityEndDate;
	}

	public AvailabilityOptions getAvailabilityOptions() {
		return availabilityOptions;
	}

	public void setAvailabilityStartDate(LocalDate availabilityStartDate) {
		this.availabilityStartDate = availabilityStartDate;
	}

	public void setAvailabilityEndDate(LocalDate availabilityEndDate) {
		this.availabilityEndDate = availabilityEndDate;
	}

	public void setAvailabilityOptions(AvailabilityOptions availabilityOptions) {
		this.availabilityOptions = availabilityOptions;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBusinessRegistration() {
		return businessRegistration;
	}

	public String getVAT() {
		return VAT;
	}

	public Double getDiscussedRate() {
		return discussedRate;
	}

	public String getAddress() {
		return address;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPhoneNumberPlanning() {
		return phoneNumberPlanning;
	}

	public String getInvoiceMail() {
		return invoiceMail;
	}

	public String getSector() {
		return sector;
	}

	public Double getKilometerRate() {
		return kilometerRate;
	}

	public DriversLicense getDriversLicense() {
		return driversLicense;
	}

	public String getAgreement() {
		return agreement;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBusinessRegistration(String businessRegistration) {
		this.businessRegistration = businessRegistration;
	}

	public void setVAT(String vAT) {
		VAT = vAT;
	}

	public void setDiscussedRate(Double discussedRate) {
		this.discussedRate = discussedRate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneNumberPlanning(String phoneNumberPlanning) {
		this.phoneNumberPlanning = phoneNumberPlanning;
	}

	public void setInvoiceMail(String invoiceMail) {
		this.invoiceMail = invoiceMail;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public void setKilometerRate(Double kilometerRate) {
		this.kilometerRate = kilometerRate;
	}

	public void setDriversLicense(DriversLicense driversLicense) {
		this.driversLicense = driversLicense;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
