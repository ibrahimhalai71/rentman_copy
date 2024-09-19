package com.rentmen.app.entities;

import com.rentmen.app.utils.AvailabilityOptions;
import com.rentmen.app.utils.DriversLicense;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
		name = "service_provider"
)
public class ServiceProvider extends User {
	private Boolean availabilityStatus;
	private Float rating;
	private String contact;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate availabilityStartDate;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate availabilityEndDate;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate unavailableStartDate;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate unavailableEndDate;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate unknownStartDate;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate unknownEndDate;
	@Enumerated(EnumType.STRING)
	private AvailabilityOptions availabilityOptions;
	@Enumerated(EnumType.STRING)
	private DriversLicense driversLicense;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	private LocalDate dateOfBirth;
	@ManyToMany(
			fetch = FetchType.EAGER
	)
	@JoinTable(
			name = "service_provider_skill",
			joinColumns = {@JoinColumn(
					name = "service_provider_id",
					referencedColumnName = "id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "skill_id",
					referencedColumnName = "id"
			)}
	)
	Set<Skill> skills = new HashSet();

	@ElementCollection
	@CollectionTable(name = "service_provider_availability_dates", joinColumns = @JoinColumn(name = "service_provider_id"))
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private List<LocalDate> availabilityDates;

	@ElementCollection
	@CollectionTable(name = "service_provider_unavailability_dates", joinColumns = @JoinColumn(name = "service_provider_id"))
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private List<LocalDate> unavailabilityDates;

	@ElementCollection
	@CollectionTable(name = "service_provider_unknown_dates", joinColumns = @JoinColumn(name = "service_provider_id"))
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private List<LocalDate> unknownDates;
	
	public ServiceProvider() {
	}

	public Boolean getAvailabilityStatus() {
		return this.availabilityStatus;
	}

	public void setAvailabilityStatus(Boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public Float getRating() {
		return this.rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Set<Skill> getSkills() {
		return this.skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public Integer getAge() {
		return this.dateOfBirth != null ? Period.between(this.dateOfBirth, LocalDate.now()).getYears() : 0;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public LocalDate getAvailabilityStartDate() {
		return this.availabilityStartDate;
	}

	public LocalDate getAvailabilityEndDate() {
		return this.availabilityEndDate;
	}

	public AvailabilityOptions getAvailabilityOptions() {
		return this.availabilityOptions;
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
		return this.driversLicense;
	}

	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDriversLicense(DriversLicense driversLicense) {
		this.driversLicense = driversLicense;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getUnavailableStartDate() {
		return unavailableStartDate;
	}

	public void setUnavailableStartDate(LocalDate unavailableStartDate) {
		this.unavailableStartDate = unavailableStartDate;
	}

	public LocalDate getUnavailableEndDate() {
		return unavailableEndDate;
	}

	public void setUnavailableEndDate(LocalDate unavailableEndDate) {
		this.unavailableEndDate = unavailableEndDate;
	}

	public LocalDate getUnknownStartDate() {
		return unknownStartDate;
	}

	public void setUnknownStartDate(LocalDate unknownStartDate) {
		this.unknownStartDate = unknownStartDate;
	}

	public LocalDate getUnknownEndDate() {
		return unknownEndDate;
	}

	public void setUnknownEndDate(LocalDate unknownEndDate) {
		this.unknownEndDate = unknownEndDate;
	}

	public List<LocalDate> getAvailabilityDates() {
		return availabilityDates;
	}

	public void setAvailabilityDates(List<LocalDate> availabilityDates) {
		this.availabilityDates = availabilityDates;
	}

	public List<LocalDate> getUnavailabilityDates() {
		return unavailabilityDates;
	}

	public void setUnavailabilityDates(List<LocalDate> unavailabilityDates) {
		this.unavailabilityDates = unavailabilityDates;
	}

	public List<LocalDate> getUnknownDates() {
		return unknownDates;
	}

	public void setUnknownDates(List<LocalDate> unknownDates) {
		this.unknownDates = unknownDates;
	}
}
