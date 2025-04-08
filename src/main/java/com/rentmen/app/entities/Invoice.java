package com.rentmen.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rentmen.app.utils.InvoiceType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
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
    @JsonIgnore
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
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType invoiceType;
    @Column(name = "day_or_hour")
    private String dayOrHour;
    @Column(name = "invoice_number")
    private Long invoiceNumber;
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

    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "invoice",
            fetch = FetchType.EAGER
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
}
