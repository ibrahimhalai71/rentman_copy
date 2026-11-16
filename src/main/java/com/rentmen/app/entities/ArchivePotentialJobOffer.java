package com.rentmen.app.entities;

import javax.persistence.*;

@Entity
@Table(
		name = "archive_potential_job_offers"
)
@IdClass(ArchivePotentialJobOfferId.class)
public class ArchivePotentialJobOffer {
	@Id
	@ManyToOne
	private ArchiveJob job;
	@Id
	@ManyToOne
	private ServiceProvider serviceProvider;
	private Integer accepted;

	public ArchivePotentialJobOffer() {
	}

	public ArchiveJob getJob() {
		return this.job;
	}

	public ServiceProvider getServiceProvider() {
		return this.serviceProvider;
	}

	public Integer getAccepted() {
		return this.accepted;
	}

	public void setJob(ArchiveJob job) {
		this.job = job;
	}

	public ArchivePotentialJobOffer(ArchiveJob job, ServiceProvider serviceProvider) {
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
}
