package com.rentmen.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(
		name = "potential_job_offers"
)
@IdClass(PotentialJobOfferId.class)
public class PotentialJobOffer {
	@Id
	@ManyToOne
	@JsonBackReference
	private Job job;
	@Id
	@ManyToOne
	private ServiceProvider serviceProvider;
	private Integer accepted;

	public PotentialJobOffer() {
	}

	public Job getJob() {
		return this.job;
	}

	public ServiceProvider getServiceProvider() {
		return this.serviceProvider;
	}

	public Integer getAccepted() {
		return this.accepted;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public PotentialJobOffer(Job job, ServiceProvider serviceProvider) {
		this.job = job;
		this.serviceProvider = serviceProvider;
		this.accepted = -1;
	}

	public PotentialJobOffer(ArchiveJob archivejob, ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
		this.accepted = -1;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
}
