
package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class InvoiceDto {
	private int id;
	private @NotEmpty JobDto job;
	@JsonProperty("service_provider")
	private @NotEmpty UserDto serviceProvider;
	private Double kilometer;
	@JsonProperty("extra_cost")
	private Double extraCost;
	@JsonProperty("extra_cost_description")
	private String extraCostDescription;
	private Boolean reviewedBySp;
	private Boolean reviewedByClient;

	public InvoiceDto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JobDto getJob() {
		return this.job;
	}

	public void setJob(JobDto job) {
		this.job = job;
	}

	public UserDto getServiceProvider() {
		return this.serviceProvider;
	}

	public void setServiceProvider(UserDto serviceProvider) {
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

	public Boolean getReviewedBySp() {
		return this.reviewedBySp;
	}

	public void setReviewedBySp(Boolean reviewedBySp) {
		this.reviewedBySp = reviewedBySp;
	}

	public Boolean getReviewedByClient() {
		return this.reviewedByClient;
	}

	public void setReviewedByClient(Boolean reviewedByClient) {
		this.reviewedByClient = reviewedByClient;
	}
}
