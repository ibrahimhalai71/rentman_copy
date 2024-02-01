package com.rentmen.app.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "service_provider")
public class ServiceProvider extends User {
	private Boolean availabilityStatus;	
	private Float rating;	
	private String description;
	private Integer age;	
	private String contact;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "service_provider_skill", 
	joinColumns = {@JoinColumn(name = "service_provider_id", referencedColumnName = "id") },
	inverseJoinColumns = {@JoinColumn(name = "skill_id", referencedColumnName = "id") })
	Set<Skill> skills = new HashSet<>();
	
	@OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private List<Job> acceptedJobs = new ArrayList<>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
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

	public List<Job> getAcceptedJobs() {
		return acceptedJobs;
	}

	public void setAcceptedJobs(List<Job> acceptedJobs) {
		this.acceptedJobs = acceptedJobs;
	}
	
	

}
