package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.utils.AvailabilityOptions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserDto {
	private long id;

	@NotEmpty
	@Size(min = 3, message = "Username must be at least 3 characters")
	private String name;

	private String email;

	@NotEmpty
	@Size(min = 4, max = 16, message = "Password should be between 4-16 characters")
	private String password;

	private String about;
	private Integer depId;
	private Boolean active;

	private Boolean status;
	private String city;
	@JsonProperty("profile_image")
	private String profileImage;
	
	//Client Attributes
	private String billingInfo;
	private String preferences;
	private String occupation;
	
	//Moderator Attributes
	private String accessLevel;

	//Service Provider Attributes
	private Boolean availabilityStatus;	
	private Integer rating;	
	private String description;
	private Integer age;	
	private String contact;
	private Set<SkillDto> skills = new HashSet<>(); 
	@JsonProperty("availability_start_date")
	private LocalDate availabilityStartDate;
	@JsonProperty("availability_end_date")
	private LocalDate availabilityEndDate;
	@JsonProperty("availability_options")
	private AvailabilityOptions availabilityOptions;
	
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getBillingInfo() {
		return billingInfo;
	}

	public void setBillingInfo(String billingInfo) {
		this.billingInfo = billingInfo;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

}
