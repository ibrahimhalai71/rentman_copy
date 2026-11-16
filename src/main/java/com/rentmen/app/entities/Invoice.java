package com.rentmen.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(
		name = "invoice"
)
public class Invoice {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	private Long id;
	@ManyToOne
	@JoinColumn(
			name = "job_id"
	)
	@JsonIgnore
	private Job job;
	@ManyToOne
	@JoinColumn(
			name = "service_provider_id"
	)
	private ServiceProvider serviceProvider;
	private Double kilometer;
	private Double extraCost;
	private String extraCostDescription;
	@Transient
	private Boolean reviewedBySP;
	@Transient
	private Boolean reviewedByClient;

	public Invoice() {
	}

	public Invoice(Job job, Double kilometer, Double extraCost, String extraCostDescription) {
		this.job = job;
		this.kilometer = kilometer;
		this.extraCost = extraCost;
		this.extraCostDescription = extraCostDescription;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public ServiceProvider getServiceProvider() {
		return this.serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Double getKilometer() {
		return this.kilometer;
	}

	public void setKilometer(Double kilometer) {
		this.kilometer = kilometer;
	}

	public Double getExtraCost() {
		return this.extraCost;
	}

	public void setExtraCost(Double extraCost) {
		this.extraCost = extraCost;
	}

	public String getExtraCostDescription() {
		return this.extraCostDescription;
	}

	public void setExtraCostDescription(String extraCostDescription) {
		this.extraCostDescription = extraCostDescription;
	}

	public Boolean getReviewedBySP() {
		return this.reviewedBySP;
	}

	public void setReviewedBySP(Boolean reviewedBySP) {
		this.reviewedBySP = reviewedBySP;
	}

	public Boolean getReviewedByClient() {
		return this.reviewedByClient;
	}

	public void setReviewedByClient(Boolean reviewedByClient) {
		this.reviewedByClient = reviewedByClient;
	}
}
