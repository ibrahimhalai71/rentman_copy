package com.rentmen.app.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client extends User {
	
	private String billingInfo;
	private String preferences;
	private String occupation;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Job> jobs = new ArrayList<>();
	
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
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	
}
