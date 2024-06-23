package com.rentmen.app.DTO;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoiceDto {
	private int id;
	
	@NotEmpty
	private JobDto job;
	
	@NotEmpty
	@JsonProperty("service_provider")
	private UserDto serviceProvider;
	
	@NotEmpty
	private Double kilometer;

	@NotEmpty
	@JsonProperty("extra_cost")
	private Double extraCost;

	@NotEmpty
	@JsonProperty("extra_cost_description")
	private String extraCostDescription;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JobDto getJob() {
		return job;
	}

	public void setJob(JobDto job) {
		this.job = job;
	}

	public UserDto getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(UserDto serviceProvider) {
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
