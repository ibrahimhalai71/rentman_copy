package com.rentmen.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.utils.InvoiceStatus;
import com.rentmen.app.utils.InvoiceType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private Job job;
    @ManyToOne
    @JoinColumn(
            name = "service_provider_id"
    )
    private ServiceProvider serviceProvider;

    @Column(name = "aantal")
    private double hoursWorked;
    @Column(name = "tarief")
    private Double spRate;
    @Column(name = "leverdatum")
    private LocalDate jobDate;
    private Double kilometer;
    @Column(name = "extra_cost")
    private Double extraCost;
    @Column(name = "extra_cost_description")
    private String extraCostDescription;
    @Column(name = "break_minutes")
    private LocalTime breakMinutes;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType invoiceType;
    @Column(name = "day_or_hour")
    private String dayOrHour;
    @Column(name = "invoice_number")
    private Long invoiceNumber;
    @Column(name = "dagen")
    private String dagen;
    @Column(name = "percentage_switch")
    private Boolean percentageSwitch;
    @Column(name = "percentage_number")
    private Long percentageNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_invoice_id", nullable = true)
    @JsonBackReference
    private Invoice parentInvoice;

    // Owning side: the list of merged/child invoices
    @OneToMany(mappedBy = "parentInvoice", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Invoice> mergedInvoices = new ArrayList<>();

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Transient
    List<MultipartFile> files;

    @ElementCollection
    @CollectionTable(name = "invoice_files", joinColumns = @JoinColumn(name = "invoice_id"))
    @Column(name = "file_path")
    List<String> filePath;

    @Column(name = "invoice_description")
    private String invoiceDescription;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status")
    private InvoiceStatus status;

    @JsonProperty("own_car")
    private Boolean ownCar;

    @Column(name = "over_time")
    private Double overtime;

    @Column(name = "travel_time")
    private Double traveltime;

    @Column(name = "total_time_worked")
    private Double workedtime;

    @Column(name = "aandacht")
    private Boolean attention;

    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "invoice",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<InvoiceLines> invoiceLines;
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

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ServiceProvider getServiceProvider() {
        return this.serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Invoice getParentInvoice() {
        return parentInvoice;
    }

    public void setParentInvoice(Invoice parentInvoice) {
        this.parentInvoice = parentInvoice;
    }

    public Double getSpRate() {
        return spRate;
    }

    public void setSpRate(Double spRate) {
        this.spRate = spRate;
    }

    public LocalDate getJobDate() {
        return jobDate;
    }

    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public String getDayOrHour() {
        return dayOrHour;
    }

    public void setDayOrHour(String dayOrHour) {
        this.dayOrHour = dayOrHour;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public void setMergedInvoices(List<Invoice> mergedInvoices) {
        this.mergedInvoices = mergedInvoices;
    }

    public List<Invoice> getMergedInvoices() {
        return mergedInvoices;
    }

    public List<InvoiceLines> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<InvoiceLines> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public String getInvoiceDescription() {
        return invoiceDescription;
    }

    public void setInvoiceDescription(String invoiceDescription) {
        this.invoiceDescription = invoiceDescription;
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

    public Double getOvertime() {
        return overtime;
    }

    public void setOvertime(Double overtime) {
        this.overtime = overtime;
    }

    public Double getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(Double traveltime) {
        this.traveltime = traveltime;
    }

    public Double getWorkedtime() {
        return workedtime;
    }

    public void setWorkedtime(Double workedtime) {
        this.workedtime = workedtime;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
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

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

    public LocalTime getBreakMinutes() {
        return breakMinutes;
    }

    public void setBreakMinutes(LocalTime breakMinutes) {
        this.breakMinutes = breakMinutes;
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
