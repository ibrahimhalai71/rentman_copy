package com.rentmen.app.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneratedInvoiceDto {
	@JsonProperty("invoices")
	List<InvoiceDto> invoices;
	@JsonProperty("total_extra_cost")
	Double totalExtraCost;
	@JsonProperty("total_kilometer")
	Double totalKilometer;
	@JsonProperty("cost")
	Double cost;
	@JsonProperty("total_cost")
	Double totalCost;
	@JsonProperty("total_cost_tax")
	Double totalCostTax;
	
	public GeneratedInvoiceDto(List<InvoiceDto> invoices, Double totalExtraCost, Double totalKilometer, Double cost,
			Double totalCost, Double totalCostTax) {
		super();
		this.invoices = invoices;
		this.totalExtraCost = totalExtraCost;
		this.totalKilometer = totalKilometer;
		this.cost = cost;
		this.totalCost = totalCost;
		this.totalCostTax = totalCostTax;
	}

	public List<InvoiceDto> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<InvoiceDto> invoices) {
		this.invoices = invoices;
	}

	public Double getTotalExtraCost() {
		return totalExtraCost;
	}

	public void setTotalExtraCost(Double totalExtraCost) {
		this.totalExtraCost = totalExtraCost;
	}

	public Double getTotalKilometer() {
		return totalKilometer;
	}

	public void setTotalKilometer(Double totalKilometer) {
		this.totalKilometer = totalKilometer;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getTotalCostTax() {
		return totalCostTax;
	}

	public void setTotalCostTax(Double totalCostTax) {
		this.totalCostTax = totalCostTax;
	}

}
