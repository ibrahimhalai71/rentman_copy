
package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.utils.InvoiceType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

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
	@JsonProperty("invoice_type")
	private InvoiceType invoiceType;
	List<MultipartFile> files;
	private LocalDate jobDate;

	private String dagen;
	@JsonProperty("percentage_switch")
	private Boolean percentageSwitch;
	@JsonProperty("percentage_number")
	private Long percentageNumber;

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

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public LocalDate getJobDate() {
		return jobDate;
	}

	public void setJobDate(LocalDate jobDate) {
		this.jobDate = jobDate;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
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

	public String getDagen() {
		return dagen;
	}

	public void setDagen(String dagen) {
		this.dagen = dagen;
	}

	public Boolean getPercentageSwitch() {
		return percentageSwitch;
	}

	public void setPercentageSwitch(Boolean percentageSwitch) {
		this.percentageSwitch = percentageSwitch;
	}

	public Long getPercentageNumber() {
		return percentageNumber;
	}

	public void setPercentageNumber(Long percentageNumber) {
		this.percentageNumber = percentageNumber;
	}
}
