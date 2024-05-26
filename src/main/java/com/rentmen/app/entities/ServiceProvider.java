package com.rentmen.app.entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.rentmen.app.utils.AvailabilityOptions;
import com.rentmen.app.utils.DriversLicense;

@Entity
@Table(name = "service_provider")
public class ServiceProvider extends User {
	private Boolean availabilityStatus;	
	private Float rating;	
	private String contact;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityEndDate;
    
    @Enumerated(EnumType.STRING)
    private AvailabilityOptions availabilityOptions;

    @Enumerated(EnumType.STRING)
    private DriversLicense driversLicense;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "service_provider_skill", joinColumns = {
			@JoinColumn(name = "service_provider_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "skill_id", referencedColumnName = "id") })
	Set<Skill> skills = new HashSet<>();

	public Boolean getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(Boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public Integer getAge() {
		if ((this.dateOfBirth != null)) {
            return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        } else {
            return 0;
        }
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public DriversLicense getDriversLicense() {
		return driversLicense;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDriversLicense(DriversLicense driversLicense) {
		this.driversLicense = driversLicense;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
