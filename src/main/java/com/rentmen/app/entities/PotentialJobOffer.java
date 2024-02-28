package com.rentmen.app.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "potential_job_offers")
@IdClass(PotentialJobOfferId.class)
public class PotentialJobOffer{

	public PotentialJobOffer() {
		super();
	}

	@Id
	@ManyToOne
	private Job job;
	
	
	@Id
	@ManyToOne
	private ServiceProvider serviceProvider;
	
	public Job getJob() {
		return job;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public PotentialJobOffer(Job job, ServiceProvider serviceProvider) {
		super();
		this.job = job;
		this.serviceProvider = serviceProvider;
		this.accepted = -1;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	private Integer accepted;

}
