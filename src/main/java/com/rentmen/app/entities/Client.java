package com.rentmen.app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rentmen.app.utils.Billable;
import com.rentmen.app.utils.ClientEventRateType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
		name = "client"
)
public class Client extends User {
	private String address;
	private String contactPerson;
	private String phoneNumber;
	private String phoneNumberPlanning;
	private String invoiceMail;
	private String sector;
	private Double kilometerRate;
	private Double minimumPaymentHour;
	@Enumerated(EnumType.STRING)
	private Billable billable;
	@Enumerated(EnumType.STRING)
	private ClientEventRateType clientEventRateType;
	@OneToMany(
			mappedBy = "client",
			cascade = {CascadeType.ALL}
	)
	@JsonManagedReference
	private List<Job> jobs = new ArrayList<>();


	public Client() {
	}

	public Billable getBillable() {
		return this.billable;
	}

	public void setBillable(Billable billable) {
		this.billable = billable;
	}

	public ClientEventRateType getClientEventRateType() {
		return clientEventRateType;
	}

	public void setClientEventRateType(ClientEventRateType clientEventRateType) {
		this.clientEventRateType = clientEventRateType;
	}

	public String getAddress() {
		return this.address;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getPhoneNumberPlanning() {
		return this.phoneNumberPlanning;
	}

	public String getInvoiceMail() {
		return this.invoiceMail;
	}

	public String getSector() {
		return this.sector;
	}

	public Double getKilometerRate() {
		return this.kilometerRate;
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

	public Double getMinimumPaymentHour() {
		return minimumPaymentHour;
	}

	public void setMinimumPaymentHour(Double minimumPaymentHour) {
		this.minimumPaymentHour = minimumPaymentHour;
	}

	public void setKilometerRate(Double kilometerRate) {
		this.kilometerRate = kilometerRate;
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
}
