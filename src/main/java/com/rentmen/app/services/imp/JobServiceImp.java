package com.rentmen.app.services.imp;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;

import com.rentmen.app.Constants.Constants;
import com.rentmen.app.DTO.*;
import com.rentmen.app.configurations.JwtTokenHelper;
import com.rentmen.app.entities.*;
import com.rentmen.app.entities.User.RateType;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.*;
import com.rentmen.app.services.JobService;
import com.rentmen.app.utils.InvoiceType;
import com.rentmen.app.utils.JobStatus;
import com.rentmen.app.utils.UtilFunctions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    @Autowired
    private ModeratorRepo moderatorRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SkillRepo skillRepo;

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private PotentialJobOfferRepo pjoRepo;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceLinesRepo invoiceLinesRepo;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public JobDto createJob(JobDto jobDto) {
        Job job = setFeilds(jobDto);
        job = jobRepo.save(job);
        return modelMapper.map(job, JobDto.class);
    }

    @Override
    public JobDto updateJob(JobDto jobDto) {
//		Job oldJob= jobRepo.findById(jobDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobDto.getId()));
        Job updatedJob = setFeilds(jobDto);
//		updatedJob.setId(oldJob.getId());
        updatedJob = jobRepo.save(updatedJob);
        return modelMapper.map(updatedJob, JobDto.class);
    }

    @Override
    public JobDto getJobById(Long id) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        return modelMapper.map(job, JobDto.class);
    }

    @Override
    public List<JobDto> getAllJobs() {
        List<Job> jobList = jobRepo.findAll();
        Type listType = new TypeToken<List<JobDto>>() {
        }.getType();
        return modelMapper.map(jobList, listType);
    }

    @Override
    @Transactional
    public Boolean deleteJob(Long id) {
        try {
            Job job = jobRepo.findById(id).get();
            invoiceRepo.deleteByJob(job);
            jobRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<JobDto> getAllJobsByClientId(Long id) {
        List<Job> jobList = jobRepo.findByClientId(id);
        Type listType = new TypeToken<List<JobDto>>() {
        }.getType();
        return modelMapper.map(jobList, listType);
    }

    @Override
    public List<JobDto> getAllJobsByModeratorId(Long id) {
        List<Job> jobList = jobRepo.findByModeratorId(id);
        Type listType = new TypeToken<List<JobDto>>() {
        }.getType();
        return modelMapper.map(jobList, listType);
    }

    @Override
    public List<JobDto> getAllJobsServiceProviderIds(List<Long> serviceProviderIds) {
        List<ServiceProvider> serviceProviders = serviceProviderIds.stream()
                .map(serviceProviderDto -> serviceProviderRepo.findById(serviceProviderDto)
                        .orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", serviceProviderDto)))
                .collect(Collectors.toList());
        List<Job> jobList = jobRepo.findByServiceProvidersListIn(serviceProviders);
        Type listType = new TypeToken<List<JobDto>>() {
        }.getType();
        return modelMapper.map(jobList, listType);
    }

    @Override
    public List<JobDto> getAllJobsCreatedById(Long id) {
        List<Job> jobList = jobRepo.findByCreatedBy(id);
        Type listType = new TypeToken<List<JobDto>>() {
        }.getType();
        return modelMapper.map(jobList, listType);
    }

    @Override
    public List<JobDto> getPotentialJobsOfferedToServiceProvider(Set<Long> serviceProviderIds) {
        Set<PotentialJobOffer> potentialServiceProviders = serviceProviderIds.stream().map(spId -> {
            ServiceProvider sp = serviceProviderRepo.findById(spId)
                    .orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", spId));
            PotentialJobOffer pjo = new PotentialJobOffer();
            return pjo;
        }).collect(Collectors.toSet());
        List<Job> jobList = jobRepo.findByPotentialJobOffersIn(potentialServiceProviders);
//		Type listType = new TypeToken<List<JobDto>>() {}.getType();
//		return modelMapper.map(jobList, listType);
        return null;
    }

    @Override
    public List<PotentialJobOfferDto> getPotentialJobOffersToServiceProvider(Long serviceProviderId) {
        List<PotentialJobOffer> pjos = pjoRepo.findbyServiceProviderId(serviceProviderId);
        Type listType = new TypeToken<List<PotentialJobOfferDto>>() {
        }.getType();
        return modelMapper.map(pjos, listType);
    }

    @Transactional
    @Override
    public int acceptJobOffers(List<Long> jobIds, Long serviceProviderId) {
        for (Long jobId : jobIds) {
            jobRepo.addToServiceProvidersList(jobId, serviceProviderId);
        }
        return pjoRepo.acceptJobOffers(jobIds, serviceProviderId);
    }

    @Override
    public int rejectJobOffers(List<Long> jobIds, Long serviceProviderId) {
        return pjoRepo.rejectJobOffers(jobIds, serviceProviderId);
    }

    private Job setFeilds(JobDto jobDto) {
        Job job;
        Client client;
        Moderator moderator = new Moderator();

        job = modelMapper.map(jobDto, Job.class);
        if (job.getInvoiceNumber() == null) {
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
            int rand = new Random().nextInt(10);
            job.setInvoiceNumber(Long.parseLong(dateTime + rand));
        }
        client = clientRepo.findById(jobDto.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", jobDto.getClient().getId()));
        job.setClient(client);

        if (jobDto.getModerator() != null) {
            moderator = moderatorRepo.findById(jobDto.getModerator().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Moderator", "id", jobDto.getModerator().getId()));
            job.setModerator(moderator);
        }

        if (jobDto.getServiceProvidersList() != null && !jobDto.getServiceProvidersList().isEmpty()) {
            List<ServiceProvider> serviceProviderList = jobDto.getServiceProvidersList().stream()
                    .map(sp -> serviceProviderRepo.findById(sp.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", sp.getId())))
                    .collect(Collectors.toList());

            job.setServiceProvidersList(serviceProviderList);
        }

        if (jobDto.getCreatedBy().getId() == client.getId()) {
            job.setCreatedBy((User) client);
        } else if (jobDto.getCreatedBy().getId() == moderator.getId()) {
            job.setCreatedBy((User) moderator);
        }

        if (!jobDto.getRequiredSkills().isEmpty()) {
            Set<Skill> requiredSkills = jobDto.getRequiredSkills().stream()
                    .map(skillDto -> skillRepo.findById(skillDto.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillDto.getId())))
                    .collect(Collectors.toSet());
            job.setRequiredSkills(requiredSkills);
        }

        if (!jobDto.getPotentialJobOffers().isEmpty()) {
            List<PotentialJobOffer> pjos = new ArrayList<PotentialJobOffer>();
            if (jobDto.getId() == null) {
                pjos = jobDto.getPotentialJobOffers().stream().map(dto -> {
                    ServiceProvider sp = serviceProviderRepo.findById(dto.getServiceProviderId()).orElseThrow(
                            () -> new ResourceNotFoundException("ServiceProvider", "id", dto.getServiceProviderId()));
                    return new PotentialJobOffer(job, sp);
                }).collect(Collectors.toList());
            } else {
//				List<PotentialJobOfferId> pjoIds = jobDto.getPotentialJobOffers().stream()
//						.filter(pjo -> jobDto.getId() != null)
//						.map(pjo -> new PotentialJobOfferId(jobDto.getId(), pjo.getServiceProviderId()))
//						.collect(Collectors.toList());
//				pjos = pjoRepo.findAllById(pjoIds);

//				HashMap<Long,PotentialJobOffer> mapSpToPjos = new HashMap<Long,PotentialJobOffer>();
//				for(PotentialJobOffer pjo : pjos) {
//					mapSpToPjos.put(pjo.getServiceProvider().getId(), pjo);
//				}
//				for(PotentialJobOfferDto pjoDto : jobDto.getPotentialJobOffers()) {
//					if(!mapSpToPjos.containsKey(pjoDto.getServiceProviderId())) {
//						ServiceProvider sp = serviceProviderRepo.findById(pjoDto.getServiceProviderId()).orElseThrow(
//								() -> new ResourceNotFoundException("ServiceProvider", "id", pjoDto.getServiceProviderId()));
//						PotentialJobOffer pjo = new PotentialJobOffer(job, sp);
//						pjos.add(pjo);
//					}
//				}

                for (PotentialJobOfferDto pjoDto : jobDto.getPotentialJobOffers()) {
                    PotentialJobOfferId pjoId = new PotentialJobOfferId(jobDto.getId(), pjoDto.getServiceProviderId());
                    Optional<PotentialJobOffer> pjo = pjoRepo.findById(pjoId);
                    if (pjo.isPresent()) {
                        pjos.add(pjo.get());
                    } else {
                        ServiceProvider sp = serviceProviderRepo.findById(pjoDto.getServiceProviderId())
                                .orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id",
                                        pjoDto.getServiceProviderId()));
                        PotentialJobOffer newPjo = new PotentialJobOffer(job, sp);
                        pjos.add(newPjo);
                    }
                }
            }
            job.setPotentialJobOffers(pjos);
        }

        return job;
    }

    @Override
    public JobDto getClientReviewForm(String token) throws Exception {
        if (jwtTokenHelper.validateTokenForReviewForm(token)) {
            Long jobId = jwtTokenHelper.getJobIdFromToken(token);
            return modelMapper.map(
                    this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId)),
                    JobDto.class);
        } else {
            throw new Exception("Error: invalid token or token expired");

        }
    }

    @Transactional
    @Override
    public void postClientReviewForm(String token, List<ReviewFormClient> reviews) throws Exception {
        if (jwtTokenHelper.validateTokenForReviewForm(token)) {
            Long jobId = jwtTokenHelper.getJobIdFromToken(token);
            Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
            if (job.getReviewedByClient() != null ? job.getReviewedByClient() : false) {
                throw new Exception("Error: review has been done");
            }
            List<ServiceProvider> spList = job.getServiceProvidersList();
            Map<Long, Float> clientReviewMap = reviews.stream()
                    .collect(Collectors.toMap(ReviewFormClient::getServiceProviderId, ReviewFormClient::getRating));
            for (ServiceProvider sp : spList) {
                Float review = clientReviewMap.get(sp.getId());
                Float rating = sp.getRating();
                Integer totalJobs = jobRepo.getReviewedJobsCount(sp.getId());
                if (review > 0 || rating > 0) {
                    rating = ((rating * totalJobs) + review) / (totalJobs + 1);
                    sp.setRating(rating);
                    serviceProviderRepo.updateRating(rating, sp.getId());
                }
            }
            jobRepo.setReviewedByClientTrue(jobId);
        } else {
            throw new Exception("Error: invalid token or token expired");

        }
    }

    @Override
    public Boolean isReviewedServiceProviderReviewForm(String token, Long serviceProviderId) throws Exception {
        if (jwtTokenHelper.validateTokenForReviewForm(token)) {
            Long jobId = jwtTokenHelper.getJobIdFromToken(token);
            Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
            Map<Long, Boolean> spReviewMap = job.getReviewedByServiceProvider();
            List<ServiceProvider> spList = job.getServiceProvidersList();
            if (!spList.stream().anyMatch(sp -> sp.getId() == serviceProviderId)) {
                throw new Exception("Error: Service-provider/student not present in job");
            }
            if (spReviewMap != null) {
                if (spReviewMap.containsKey(serviceProviderId)) {
                    return spReviewMap.get(serviceProviderId);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            throw new Exception("Error: invalid token or token expired");

        }
    }

    @Override
    public Boolean isOverriddenByClientBeforeServiceProviderReviewForm(String token, Long serviceProviderId) throws Exception {
        if (jwtTokenHelper.validateTokenForReviewForm(token)) {
            Long jobId = jwtTokenHelper.getJobIdFromToken(token);
            Optional<List<Invoice>> invoiceList = this.invoiceRepo.findByJobId(jobId);
            if (invoiceList.isPresent()) {
                List<Invoice> invoices = invoiceList.get();
                ServiceProvider sp = invoices.stream()
                        .map(Invoice::getServiceProvider)
                        .filter(provider -> provider.getId() == serviceProviderId)
                        .findAny()
                        .orElse(null);
                return sp != null && !isReviewedServiceProviderReviewForm(token, serviceProviderId);
            }
            return false;
        } else {
            throw new Exception("Error: invalid token or token expired");
        }
    }

    @Override
    public Invoice createInvoice(Long jobId) throws Exception {
        Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
        if (job.getStatus().equals(JobStatus.COMPLETED)) {
            Optional<List<Invoice>> existingInvoice = invoiceRepo.findByJobId(jobId);
            if (existingInvoice.isPresent()) {
                List<Invoice> presentInvoices = existingInvoice.get();
                Optional<Invoice> clientInvoice = presentInvoices
                        .stream()
                        .filter(inv -> inv.getInvoiceType().equals(InvoiceType.INVOICE)).findFirst();
                if (clientInvoice.isPresent()) {
                    if (clientInvoice.get().getParentInvoice() != null) {
                        Invoice parentInvoice = clientInvoice.get().getParentInvoice();
                        parentInvoice.getMergedInvoices().forEach(inv -> parentInvoice.getInvoiceLines().addAll(inv.getInvoiceLines()));
                        return parentInvoice;
                    } else {
                        return clientInvoice.get();
                    }
                }
            }
            //Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
            Invoice invoice = new Invoice();
            invoice.setJob(job);
            invoice.setInvoiceDate(LocalDate.now());

            List<InvoiceLines> lines = new ArrayList<>();
            populateInvoiceLines(lines, job, invoice);
            if (job.getClient().getRateType().equals(RateType.DAY)) {
                invoice.setHoursWorked(1);
            }
            invoice.setInvoiceNumber(job.getInvoiceNumber());
            invoice.setInvoiceLines(lines);
            invoice.setInvoiceType(InvoiceType.INVOICE);
            invoice.setDayOrHour(job.getClient().getRateType().equals(RateType.HOURLY) ? "Hour" : "Day");
            return this.invoiceRepo.save(invoice);
        } else {
            throw new Exception("Job has not finished yet");
        }
    }

    @Override
    public Invoice mergeInvoices(Long invoiceNumber, List<Long> jobIds) {
        Invoice invoice = new Invoice();
        List<Invoice> existingInvoices = invoiceRepo.findAllByJobIdIn(jobIds);
        List<Invoice> clientInvoices = existingInvoices
                .stream()
                .filter(inv -> inv.getInvoiceType().equals(InvoiceType.INVOICE))
                .collect(Collectors.toList());
        Job job = clientInvoices.get(0).getJob();
        //invoice.setJob(job);
        invoice.setInvoiceDate(LocalDate.now());
        List<InvoiceLines> lines = clientInvoices.stream()
                .flatMap(il -> il.getInvoiceLines().stream())
                .map(il -> invoiceLinesRepo.save(il))
                .collect(Collectors.toList());
        invoice.setInvoiceLines(lines);
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        int rand = new Random().nextInt(10);
        invoice.setInvoiceNumber(Long.parseLong(dateTime + rand));
        invoice.setInvoiceType(InvoiceType.INVOICE);
        clientInvoices.forEach(i -> i.setParentInvoice(invoice));
        invoice.setMergedInvoices(clientInvoices);
        return this.invoiceRepo.save(invoice);
    }

    public void populateInvoiceLines(List<InvoiceLines> lines, Job job, Invoice invoice) {
        job.getServiceProvidersList().stream().forEach(sp -> {
            InvoiceLines line = new InvoiceLines();
            double hoursWorked = ChronoUnit.MINUTES.between(job.getStartTime(), job.getEndTime()) / 60.0;
            if (job.getClient().getRateType().equals(RateType.HOURLY) && hoursWorked < job.getClient().getMinimumPaymentHour()) {
                line.setAantal(job.getClient().getMinimumPaymentHour());
                invoice.setHoursWorked(job.getClient().getMinimumPaymentHour());
            } else if (job.getClient().getRateType().equals(RateType.HOURLY) && hoursWorked >= job.getClient().getMinimumPaymentHour()) {
                line.setAantal(hoursWorked);
                invoice.setHoursWorked(hoursWorked);
            } else if (job.getClient().getRateType().equals(RateType.DAY)) {
                line.setAantal(1);
            }
            String skillName = "";
            Set<Skill> skill = job.getRequiredSkills();
            if (!skill.isEmpty()) {
                List<String> skillNames = skill.stream()
                        .map(Skill::getName)
                        .collect(Collectors.toList());
                skillName = skillNames.get(0);
            }
            line.setOmschrijving(job.getStartDate() + " / " + "Project: " + job.getInvoiceNumber() + " / " + skillName);
            line.setPrice(UtilFunctions.clientsRateOnEventTypeAndJobTime(job, sp));
            line.setInvoice(invoice);
            lines.add(line);
        });
    }

    @Override
    public Invoice updateInvoice(Long invoiceId, Invoice invoice) {
        if (invoice.getInvoiceType().equals(InvoiceType.INVOICE)) {
            return updateClientInvoice(invoiceId, invoice);
        } else {
            return updatePurchaseOrder(invoiceId, invoice);
        }
    }

    public Invoice updateClientInvoice(Long invoiceId, Invoice invoice) {
        invoice.setId(invoiceId);
        invoice.getMergedInvoices().forEach(inv -> {
            inv.getInvoiceLines().forEach(invLn -> {
                invoice.getInvoiceLines().forEach(pInvLn -> {
                    if (Objects.equals(pInvLn.getId(), invLn.getId())) {
                        invLn.setAantal(pInvLn.getAantal());
                        invLn.setPrice(pInvLn.getPrice());
                        invLn.setOmschrijving(pInvLn.getOmschrijving());
                    }
                });
            });
        });
        invoice.getInvoiceLines().forEach(line -> {
            line.setInvoice(invoice);
        });
        return invoiceRepo.save(invoice);
    }

    public Invoice updatePurchaseOrder(Long invoiceId, Invoice invoice) {
        invoice.setId(invoiceId);
        return invoiceRepo.save(invoice);
    }

    @Transactional
    @Override
    public Boolean deleteInvoice(Long invoiceId) {
        try {
            Invoice invoice = invoiceRepo.findById(invoiceId).get();

            if (invoice.getInvoiceType().equals(InvoiceType.INVOICE)) {
                for (Invoice childInvoice : invoice.getMergedInvoices()) {
                    childInvoice.setParentInvoice(null);
                    List<InvoiceLines> lines = new ArrayList<>();
                    populateInvoiceLines(lines, childInvoice.getJob(), childInvoice);
                    childInvoice.setInvoiceLines(lines);
                    invoiceRepo.save(childInvoice);
                }
                invoice.setMergedInvoices(new ArrayList<>());
                invoiceRepo.save(invoice);
            }

            invoiceRepo.deleteById(invoice.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        List<Invoice> initialList = invoiceRepo.findAll();
        initialList.forEach(inv -> {
            if (inv.getParentInvoice() != null) {
                Invoice parentInvoice = inv.getParentInvoice();
                parentInvoice.getInvoiceLines().addAll(inv.getInvoiceLines());
            }
        });
        return initialList
                .stream()
                .filter(inv -> inv.getParentInvoice() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Resource> getFilesForInvoice(Long jobId) {
        List<Resource> resources = new ArrayList<>();
        List<Invoice> invoices = invoiceRepo.findByJobId(jobId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + jobId));

        List<String> filePaths = invoices.stream()
                .filter(inv -> inv.getFilePath() != null)
                .flatMap(inv -> inv.getFilePath().stream())
                .collect(Collectors.toList());

        for (String filePath : filePaths) {
            Path path = Paths.get("src/main/resources/").resolve(filePath);
            Resource resource = null;
            try {
                resource = new UrlResource(path.toUri());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            if (resource.exists()) {
                resources.add(resource);
            }
        }
        return resources;
    }

//    @Override
//    public byte[] generateInvoicePdf(Long invoiceId) throws Exception {
//        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PdfWriter.getInstance(document, outputStream);
//
//        document.open();
//
//        // Fonts
//        Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//        Font normal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
//        Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
//
//        // Company Header
//        Paragraph companyHeader = new Paragraph("EVENTICO", headerFont);
//        companyHeader.setAlignment(Element.ALIGN_LEFT);
//        document.add(companyHeader);
//        document.add(new Paragraph("Eventico B.V.\nGewandeweg 39\n5345HM Oss\nT: 0648908818\nE: administratie@eventico.nl\nI: eventico.nl", normal));
//        document.add(Chunk.NEWLINE);
//
//        // Customer Info
//        document.add(new Paragraph("Factuur", bold));
//        document.add(new Paragraph("Story Event Engineering\nZandkamp\n6626KV Alphen", normal));
//        document.add(Chunk.NEWLINE);
//
//        // Payment Info Table
//        PdfPTable paymentTable = new PdfPTable(2);
//        paymentTable.setWidthPercentage(100);
//        paymentTable.setWidths(new float[]{3, 7});
//        paymentTable.addCell(getCell("Te betalen", bold));
//        paymentTable.addCell(getCell("€ 1.060,15 (voor 20-04-2025)", normal));
//        paymentTable.addCell(getCell("Naar IBAN", bold));
//        paymentTable.addCell(getCell("NL90 KNAB 0510 4223 73", normal));
//        paymentTable.addCell(getCell("Op naam van", bold));
//        paymentTable.addCell(getCell("Eventico B.V.", normal));
//        paymentTable.addCell(getCell("Omschrijving", bold));
//        paymentTable.addCell(getCell("Factuur 250032", normal));
//        document.add(paymentTable);
//        document.add(Chunk.NEWLINE);
//
//        // Invoice Meta Info
//        PdfPTable metaTable = new PdfPTable(2);
//        metaTable.setWidthPercentage(100);
//        metaTable.setWidths(new float[]{3, 3});
//        metaTable.addCell(getCell("Factuurnummer: 250032", normal));
//        metaTable.addCell(getCell("Factuurdatum: 21-03-2025", normal));
//        metaTable.addCell(getCell("Betalingstermijn: 30 dagen", normal));
//        metaTable.addCell(getCell("Klantnummer: 2", normal));
//        metaTable.addCell(getCell("Leverdatum: 21-03-2025", normal));
//        metaTable.addCell(getCell("", normal));
//        document.add(metaTable);
//        document.add(Chunk.NEWLINE);
//
//        // Line Items Table
//        PdfPTable itemTable = new PdfPTable(4);
//        itemTable.setWidthPercentage(100);
//        itemTable.setWidths(new float[]{6, 1, 2, 2});
//        itemTable.addCell(getCell("Omschrijving", bold));
//        itemTable.addCell(getCell("Aantal", bold));
//        itemTable.addCell(getCell("Prijs", bold));
//        itemTable.addCell(getCell("Totaal", bold));
//
//        itemTable.addCell(getCell("Proj.nr. 20240832, d.d. 20-03-2025, locatie Dronten klu, medewerker 1", normal));
//        itemTable.addCell(getCell("1,00", normal));
//        itemTable.addCell(getCell("400,00", normal));
//        itemTable.addCell(getCell("400,00", normal));
//
//        itemTable.addCell(getCell("Proj.nr. 20240832, d.d. 20-03-2025, locatie Dronten klu, medewerker 2", normal));
//        itemTable.addCell(getCell("1,00", normal));
//        itemTable.addCell(getCell("400,00", normal));
//        itemTable.addCell(getCell("400,00", normal));
//
//        itemTable.addCell(getCell("Proj.nr. 20240832, d.d. 20-03-2025, locatie Dronten klu, reiskosten\nOss-Dronten-Oss (272 km)", normal));
//        itemTable.addCell(getCell("1,00", normal));
//        itemTable.addCell(getCell("76,16", normal));
//        itemTable.addCell(getCell("76,16", normal));
//
//        document.add(itemTable);
//        document.add(Chunk.NEWLINE);
//
//        // Totals Table
//        PdfPTable totals = new PdfPTable(2);
//        totals.setWidthPercentage(40);
//        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        totals.setWidths(new float[]{3, 2});
//        totals.addCell(getCell("Totaal excl. btw", bold));
//        totals.addCell(getCell("€ 876,16", normal));
//        totals.addCell(getCell("Totaal btw", bold));
//        totals.addCell(getCell("€ 183,99", normal));
//        totals.addCell(getCell("Te betalen", bold));
//        totals.addCell(getCell("€ 1.060,15", bold));
//        document.add(totals);
//
//        document.close();
//        return outputStream.toByteArray();
//    }
//
//    private PdfPCell getCell(String text, Font font) {
//        PdfPCell cell = new PdfPCell(new Phrase(text, font));
//        cell.setPadding(5);
//        cell.setBorder(Rectangle.NO_BORDER);
//        return cell;
//    }

    public byte[] generateInvoicePdf(Long invoiceId) throws Exception {
        Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow(() -> new ResourceNotFoundException("Invoice",invoiceId+"",""));



        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        InputStream imageStream = getClass().getClassLoader().getResourceAsStream("images/IMG-20250205-WA0007.jpg");
        if (imageStream == null) {
            throw new FileNotFoundException("Image not found: images/trioliet.jpg");
        }

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100.0F);
        PdfPCell cell3 = new PdfPCell();

        Image logoImage = Image.getInstance(ImageIO.read(imageStream), null);
        logoImage.scaleToFit(150, 150);
        logoImage.setAlignment(Element.ALIGN_CENTER);
        cell3.addElement(logoImage);
        cell3.setBorder(0);
        mainTable.addCell(cell3);

        mainTable.setSpacingAfter(10.0F);

        document.add(mainTable);

        // Fonts
        Font bold = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        Font normal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

        // Top-right Company Info
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);
        headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell headerCell = new PdfPCell();
        headerCell.setBorder(Rectangle.NO_BORDER);
        headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Paragraph companyHeader = new Paragraph("EVENTICO", headerFont);
        Paragraph companyDetails = new Paragraph(
                "Eventico B.V.\nGewandeweg 39\n5345HM Oss\nT: 0648908818\nE: administratie@eventico.nl\nI: eventico.nl",
                normal
        );
        companyHeader.setAlignment(Element.ALIGN_RIGHT);
        companyDetails.setAlignment(Element.ALIGN_RIGHT);
        headerCell.addElement(companyHeader);
        headerCell.addElement(companyDetails);
        headerTable.addCell(headerCell);
        document.add(headerTable);

        document.add(Chunk.NEWLINE);

        // Left-aligned invoice label and recipient
        Paragraph factuurLabel = new Paragraph("Factuur", bold);
        factuurLabel.setSpacingAfter(5);
        document.add(factuurLabel);
        document.add(new Paragraph("Story Event Engineering\nZandkamp\n6626KV Alphen", normal));

        document.add(Chunk.NEWLINE);

        // Payment Info block aligned to right
        PdfPTable paymentInfo = new PdfPTable(2);
        paymentInfo.setTotalWidth(300);
        paymentInfo.setLockedWidth(true);
        paymentInfo.setHorizontalAlignment(Element.ALIGN_RIGHT);
        paymentInfo.setWidths(new float[]{3f, 5f});
        paymentInfo.addCell(getRightCell("Te betalen:", bold));
        paymentInfo.addCell(getRightCell("€ 1.060,15 (voor 20-04-2025)", normal));
        paymentInfo.addCell(getRightCell("Naar IBAN:", bold));
        paymentInfo.addCell(getRightCell("NL90 KNAB 0510 4223 73", normal));
        paymentInfo.addCell(getRightCell("Op naam van:", bold));
        paymentInfo.addCell(getRightCell("Eventico B.V.", normal));
        paymentInfo.addCell(getRightCell("Omschrijving:", bold));
        paymentInfo.addCell(getRightCell("Factuur 250032", normal));
        document.add(paymentInfo);

        document.add(Chunk.NEWLINE);

        // Meta info block aligned right
        PdfPTable metaInfo = new PdfPTable(2);
        metaInfo.setTotalWidth(300);
        metaInfo.setLockedWidth(true);
        metaInfo.setHorizontalAlignment(Element.ALIGN_RIGHT);
        metaInfo.setWidths(new float[]{3f, 4f});
        metaInfo.addCell(getRightCell("Factuurnummer:", bold));
        metaInfo.addCell(getRightCell("250032", normal));
        metaInfo.addCell(getRightCell("Factuurdatum:", bold));
        metaInfo.addCell(getRightCell("21-03-2025", normal));
        metaInfo.addCell(getRightCell("Betalingstermijn:", bold));
        metaInfo.addCell(getRightCell("30 dagen", normal));
        metaInfo.addCell(getRightCell("Klantnummer:", bold));
        metaInfo.addCell(getRightCell("2", normal));
        metaInfo.addCell(getRightCell("Leverdatum:", bold));
        metaInfo.addCell(getRightCell("21-03-2025", normal));
        document.add(metaInfo);

        document.add(Chunk.NEWLINE);

        // Line Items Table
        PdfPTable items = new PdfPTable(4);
        items.setWidthPercentage(100);
        items.setWidths(new float[]{6, 1, 2, 2});
        items.addCell(getHeaderCell("Omschrijving"));
        items.addCell(getHeaderCell("Aantal"));
        items.addCell(getHeaderCell("Prijs"));
        items.addCell(getHeaderCell("Totaal"));

        items.addCell(getBodyCell("Proj.nr. 20240832, d.d. 20-03-2025, locatie Dronten klu, medewerker 1"));
        items.addCell(getBodyCell("1,00"));
        items.addCell(getBodyCell("400,00"));
        items.addCell(getBodyCell("400,00"));

        items.addCell(getBodyCell("Proj.nr. 20240832, d.d. 20-03-2025, locatie Dronten klu, medewerker 2"));
        items.addCell(getBodyCell("1,00"));
        items.addCell(getBodyCell("400,00"));
        items.addCell(getBodyCell("400,00"));

        items.addCell(getBodyCell("Proj.nr. 20240832, d.d. 20-03-2025, locatie Dronten klu, reiskosten\nOss-Dronten-Oss (272 km)"));
        items.addCell(getBodyCell("1,00"));
        items.addCell(getBodyCell("76,16"));
        items.addCell(getBodyCell("76,16"));

        document.add(items);
        document.add(Chunk.NEWLINE);

        // Totals Table aligned right
        PdfPTable totals = new PdfPTable(2);
        totals.setTotalWidth(200);
        totals.setLockedWidth(true);
        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totals.setWidths(new float[]{2f, 2f});
        totals.addCell(getRightCell("Totaal excl. btw:", bold));
        totals.addCell(getRightCell("€ 876,16", normal));
        totals.addCell(getRightCell("Totaal btw:", bold));
        totals.addCell(getRightCell("€ 183,99", normal));
        totals.addCell(getRightCell("Te betalen:", bold));
        totals.addCell(getRightCell("€ 1.060,15", bold));
        document.add(totals);

        document.close();
        return outputStream.toByteArray();
    }

    private PdfPCell getRightCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell getHeaderCell(String text) {
        Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell getBodyCell(String text) {
        Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        return cell;
    }

    public List<Invoice> createAllPurchaseOrders(Long jobId) throws Exception {

        List<Invoice> purchaseOrders = new ArrayList<>();
        Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
        if (job.getStatus().equals(JobStatus.COMPLETED)) {
            job.getServiceProvidersList().forEach(sp -> {
                purchaseOrders.add(createPurchaseOrder(job.getId(), sp.getId(), null, false));
            });
            return purchaseOrders;
        } else {
            throw new Exception("Job has not finished yet");
        }
    }

    private Invoice createPurchaseOrder(Long jobId, Long serviceProviderId, InvoiceDto invoiceDto, boolean isSpReview) {
        Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
        Map<Long, Boolean> spReviewMap = job.getReviewedByServiceProvider();
        if (spReviewMap == null) {
            spReviewMap = new HashMap<>();
        }
        Optional<List<Invoice>> existingInvoice = invoiceRepo.findByJobId(jobId);
        if (existingInvoice.isPresent()) {
            List<Invoice> presentInvoices = existingInvoice.get();
            Optional<Invoice> spInvoice = presentInvoices
                    .stream()
                    .filter(inv -> inv.getInvoiceType().equals(InvoiceType.PURCHASE) && inv.getServiceProvider().getId() == serviceProviderId)
                    .findFirst();
            if (spInvoice.isPresent()) {
                return spInvoice.get();
            }

        }
        Invoice invoice = new Invoice();
        if (isSpReview) {
            invoice = modelMapper.map(invoiceDto, Invoice.class);
        }
        if (!spReviewMap.containsKey(serviceProviderId) || !spReviewMap.get(serviceProviderId)) {
            if (isSpReview) {
                spReviewMap.put(serviceProviderId, true);
                String strSPReviewMap = UtilFunctions.convertWithStream(spReviewMap);
                this.jobRepo.setReviewedByServiceProviderTrue(strSPReviewMap, jobId);
                List<String> savedPaths = new ArrayList<>();
                for (MultipartFile file : invoiceDto.getFiles()) {

                    if (!file.isEmpty()) {
                        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                        Path filePath = Paths.get(uploadDir, filename);

                        try {
                            Files.createDirectories(filePath.getParent());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // Save relative path
                        savedPaths.add("uploads/" + filename);
                    }
                }
                invoice.setFilePath(savedPaths);
            } else {
                spReviewMap.put(serviceProviderId, false);
                String strSPReviewMap = UtilFunctions.convertWithStream(spReviewMap);
                this.jobRepo.setReviewedByServiceProviderTrue(strSPReviewMap, jobId);
            }
            invoice.setJob(job);
            ServiceProvider sp = this.serviceProviderRepo.findById(serviceProviderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
            invoice.setServiceProvider(sp);
            invoice.setInvoiceType(InvoiceType.PURCHASE);
            invoice.setJobDate(job.getEndDate());
            double hoursWorked = ChronoUnit.MINUTES.between(job.getStartTime(), job.getEndTime()) / 60.0;
            if (job.getClient().getRateType().equals(RateType.HOURLY)) {
                invoice.setHoursWorked(hoursWorked);
            } else {
                invoice.setHoursWorked(1);
            }
            invoice.setInvoiceDate(LocalDate.now());
            invoice.setDayOrHour(job.getClient().getRateType().equals(RateType.HOURLY) ? "Hour" : "Day");
            invoice.setSpRate(UtilFunctions.studentsRateBasedOnEventTypeAndJobTime(job, sp));
            return this.invoiceRepo.save(invoice);
        } else {
            return null;
        }
    }


    @Override
    public void postReviewFormServiceProvider(String token, InvoiceDto invoiceDto) throws Exception {

        Long serviceProviderId = invoiceDto.getServiceProvider().getId();
        if (!isReviewedServiceProviderReviewForm(token, serviceProviderId)) {
            Long jobId = jwtTokenHelper.getJobIdFromToken(token);
            createPurchaseOrder(jobId, serviceProviderId, invoiceDto, true);
        } else {
            throw new Exception("Error: review form already filled");
        }
    }

    public GeneratedInvoiceDto getInvoice(Long jobId) throws Exception {
        Optional<List<Invoice>> invoiceList = this.invoiceRepo.findByJobId(jobId);
        Job job = this.jobRepo.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
        Double extraCost = 0.0;
        Double kilometer = 0.0;
        if (invoiceList.isPresent()) {
            extraCost = invoiceList.get().stream().mapToDouble(Invoice::getExtraCost).sum();
            kilometer = invoiceList.get().stream().mapToDouble(Invoice::getKilometer).sum();

            invoiceList.get().stream().peek(invoice -> {
                boolean reviewedBySP = job.getReviewedByServiceProvider()
                        .getOrDefault(invoice.getServiceProvider().getId(), false);
                if (reviewedBySP) {
                    invoice.setReviewedBySP(true);
                }

                if (job.getReviewedByClient()) {
                    invoice.setReviewedByClient(true);
                }
            }).collect(Collectors.toList());
        }
        Double cost = getJobCost(job);
        Double kilometerRate = job.getClient().getKilometerRate() != null ? job.getClient().getKilometerRate() : 0.0;
        Double totalCost = cost + extraCost + (kilometer * kilometerRate);
        Double totalCostTax = totalCost * (Constants.VAT + 1);
        Type listType = new TypeToken<List<InvoiceDto>>() {
        }.getType();
        List<InvoiceDto> invoiceListDto = this.modelMapper.map(invoiceList, listType);
        GeneratedInvoiceDto generatedInvoiceDto = new GeneratedInvoiceDto(invoiceListDto, extraCost, kilometer, cost,
                totalCost, totalCostTax);
        return generatedInvoiceDto;
    }

    public Double getJobCost(Job job) {
        Double cost = 0.0;
        Double clientRate = job.getClient().getDiscussedRate() != null ? job.getClient().getDiscussedRate() : 0.0;
        Long days = ChronoUnit.DAYS.between(job.getStartDate(), job.getEndDate());
        Long hours = ChronoUnit.HOURS.between(job.getStartTime(), job.getEndTime());
        LocalTime nightStart = LocalTime.of(18, 0);
        LocalTime nightEnd = LocalTime.of(6, 0);
        DayOfWeek dayOfWeek = job.getStartDate().getDayOfWeek();
        ;
        if (job.getClient().getRateType() == RateType.DAY) {
            cost = days * clientRate;
        } else {
//            if (job.getClient().getDayRate() != null && job.getStartTime().isAfter(nightEnd) && job.getStartTime().isBefore(nightStart))
//                cost = (days * hours) * job.getClient().getDayRate();
//            else if (job.getClient().getNightRate() != null)
//                cost = (days * hours) * job.getClient().getNightRate();
//
//
//            if (job.getClient().getWeekendRate() != null && (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY))
//                cost = (days * hours) * job.getClient().getWeekendRate();
//            else if (job.getClient().getDayRate() == null && job.getClient().getNightRate() == null && job.getClient().getWeekendRate() == null)
//                cost = (days * hours) * clientRate;
//        }
            return cost;
        }
        return cost;
    }


}
