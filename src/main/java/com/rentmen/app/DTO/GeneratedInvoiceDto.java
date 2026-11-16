
package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

	public GeneratedInvoiceDto(List<InvoiceDto> invoices, Double totalExtraCost, Double totalKilometer, Double cost, Double totalCost, Double totalCostTax) {
		this.invoices = invoices;
		this.totalExtraCost = totalExtraCost;
		this.totalKilometer = totalKilometer;
		this.cost = cost;
		this.totalCost = totalCost;
		this.totalCostTax = totalCostTax;
	}

	public List<InvoiceDto> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<InvoiceDto> invoices) {
		this.invoices = invoices;
	}

	public Double getTotalExtraCost() {
		return this.totalExtraCost;
	}

	public void setTotalExtraCost(Double totalExtraCost) {
		this.totalExtraCost = totalExtraCost;
	}

	public Double getTotalKilometer() {
		return this.totalKilometer;
	}

	public void setTotalKilometer(Double totalKilometer) {
		this.totalKilometer = totalKilometer;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getTotalCostTax() {
		return this.totalCostTax;
	}

	public void setTotalCostTax(Double totalCostTax) {
		this.totalCostTax = totalCostTax;
	}
}
