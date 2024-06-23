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

}
