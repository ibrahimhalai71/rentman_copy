package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PotentialJobOfferDto {
	@JsonProperty("job_id")
	private Long jobId;
	@JsonProperty("service_provider_id")
	private Long serviceProviderId;
	public Long getJobId() {
		return jobId;
	}
	public Long getServiceProviderId() {
		return serviceProviderId;
	}
	public Integer getAccepted() {
		return accepted;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
	private Integer accepted;
}
