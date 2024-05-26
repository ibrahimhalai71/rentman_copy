package com.rentmen.app.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client extends User {
	
	private String address;
	private String contactPerson;
	private String phoneNumber;
	private String phoneNumberPlanning;
	private String invoiceMail;
	private String sector;
	private Double kilometerRate;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Job> jobs = new ArrayList<>();
	
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
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	
}
