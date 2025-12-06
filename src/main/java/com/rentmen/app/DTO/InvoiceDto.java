
package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.utils.InvoiceStatus;
import com.rentmen.app.utils.InvoiceType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("break_minutes")
    private LocalTime breakMinutes;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("start_time")
    private LocalTime startTime;

    private InvoiceStatus status;

    private Boolean attention;

    @JsonProperty("own_car")
    private Boolean ownCar;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("overtime")
    private Double overtime;

    @JsonProperty("travel_time")
    private Double traveltime;

    @JsonProperty("total_time_worked")
    private Double workedtime;


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

    public LocalTime getBreakMinutes() {
        return breakMinutes;
    }

    public void setBreakMinutes(LocalTime breakMinutes) {
        this.breakMinutes = breakMinutes;
    }

    public void setOvertime(Double overtime) {
        this.overtime = overtime;
    }

    public void setTraveltime(Double traveltime) {
        this.traveltime = traveltime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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

    public Double getOvertime() {
        return overtime;
    }

    public Double getTraveltime() {
        return traveltime;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Boolean getOwnCar() {
        return ownCar;
    }

    public void setOwnCar(Boolean ownCar) {
        this.ownCar = ownCar;
    }

    public Double getWorkedtime() {
        return workedtime;
    }

    public void setWorkedtime(Double workedtime) {
        this.workedtime = workedtime;
    }

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
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
