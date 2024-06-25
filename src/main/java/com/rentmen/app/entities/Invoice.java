package com.rentmen.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "invoice")
public class Invoice {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name ="job_id")
	@JsonIgnore
	private Job job;
	
	@ManyToOne
	@JoinColumn(name = "service_provider_id")
	private ServiceProvider serviceProvider;
	
	private Double kilometer;
	private Double extraCost;
	private String extraCostDescription;
	
	public Invoice() {
	}
	
	public Invoice(Job job, Double kilometer, Double extraCost, String extraCostDescription) {
		super();
		this.job = job;
		this.kilometer = kilometer;
		this.extraCost = extraCost;
		this.extraCostDescription = extraCostDescription;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Double getKilometer() {
		return kilometer;
	}
	public void setKilometer(Double kilometer) {
		this.kilometer = kilometer;
	}
	public Double getExtraCost() {
		return extraCost;
	}
	public void setExtraCost(Double extraCost) {
		this.extraCost = extraCost;
	}
	public String getExtraCostDescription() {
		return extraCostDescription;
	}
	public void setExtraCostDescription(String extraCostDescription) {
		this.extraCostDescription = extraCostDescription;
	}
	
}
