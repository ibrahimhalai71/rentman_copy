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
import io.jsonwebtoken.Claims;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
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

    @Transactional
    @Override
    public JobDto createJob(JobDto jobDto) {
        Job job = setFeilds(jobDto);
        job = jobRepo.save(job);
        acceptingNewJobs(job);
        return getJobById(job.getId());
        //return modelMapper.map(updatedJb, JobDto.class);
    }

    @Transactional
    public void acceptingNewJobs(Job job) {
        List<Long> jobIds = Collections.singletonList(job.getId());
        List<Long> serviceProviderIds = job.getPotentialJobOffers()
                .stream()
                .map(PotentialJobOffer::getServiceProvider)
                .map(ServiceProvider::getId)
                .collect(Collectors.toList());
        acceptNewJobOffers(jobIds, serviceProviderIds);
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

    @Transactional
    public void acceptNewJobOffers(List<Long> jobIds, List<Long> serviceProviderIds) {
        for (Long jobId : jobIds) {
            for (Long serviceProviderId : serviceProviderIds) {
                jobRepo.addToServiceProvidersList(jobId, serviceProviderId);
            }
        }
        pjoRepo.acceptNewJobOffers(jobIds, serviceProviderIds);
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
        job.setModeratorApproval(1);
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
                    }
                    return clientInvoice.get();
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

    @Override
    public InvoiceDto updateInvoiceTimesheet(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceRepo.findById((long) invoiceDto.getId()).get();
        invoice.setAttention(invoiceDto.getAttention());
        invoice.setStatus(invoiceDto.getStatus());
        invoice.setWorkedtime(invoiceDto.getWorkedtime());
        invoice.setOvertime(invoiceDto.getOvertime());
        invoice.setKilometer(invoiceDto.getKilometer());
        invoice.setTraveltime(invoiceDto.getTraveltime());
        invoice.setExtraCostDescription(invoiceDto.getExtraCostDescription());
        Invoice updatedInvoice = invoiceRepo.save(invoice);
        return modelMapper.map(updatedInvoice, InvoiceDto.class);
    }

    public Invoice updateClientInvoice(Long invoiceId, Invoice invoice) {
        invoice.getInvoiceLines().removeIf(Objects::isNull);
        Invoice invoice1 = invoiceRepo.findById(invoiceId).get();
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

        if (invoice1.getInvoiceLines().size() != invoice.getInvoiceLines().size()) {
            List<Long> currentIds = invoice.getInvoiceLines()
                    .stream()
                    .map(InvoiceLines::getId)
                    .collect(Collectors.toList());

            List<Long> toDeleteIds = invoice1.getInvoiceLines()
                    .stream()
                    .map(InvoiceLines::getId)
                    .filter(id -> !currentIds.contains(id))
                    .collect(Collectors.toList());

            invoice1.getInvoiceLines().removeIf(line ->
                    line.getId() != null && !currentIds.contains(line.getId())
            );
            //toDeleteIds.forEach(id -> invoiceLinesRepo.deleteById(id));
        }

        invoice.setJob(invoice1.getJob());
        invoice.getInvoiceLines().forEach(line -> {
            if (line != null) {
                line.setInvoice(invoice);
            }
        });
        return invoiceRepo.save(invoice);
    }

    public Invoice updatePurchaseOrder(Long invoiceId, Invoice invoice) {
        Invoice invoice1 = invoiceRepo.findById(invoiceId).get();
        invoice.setJob(invoice1.getJob());
        invoice.setId(invoiceId);
        invoice.getInvoiceLines().forEach(invLn -> {
            invoice.getInvoiceLines().forEach(pInvLn -> {
                if (Objects.equals(pInvLn.getId(), invLn.getId())) {
                    invLn.setAantal(pInvLn.getAantal());
                    invLn.setPrice(pInvLn.getPrice());
                    invLn.setOmschrijving(pInvLn.getOmschrijving());
                }
            });
        });
        invoice.getInvoiceLines().forEach(line -> {
            line.setInvoice(invoice);
        });
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

    @Override
    public byte[] generatePdf(Long invoiceId) throws Exception {
        Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow(() -> new ResourceNotFoundException("Invoice", invoiceId + "", ""));
        if (invoice.getInvoiceType().equals(InvoiceType.INVOICE)) {
            return generateInvoicePdf(invoice);
        } else {
            return generatePOPdf(invoice);
        }
    }

    public byte[] generatePOPdf(Invoice invoice) throws Exception {

        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font reallyBigBoldFont = new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD);
        Font bigBoldFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font regularFont = new Font(Font.FontFamily.HELVETICA, 10);

        // Header with logo and address
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new int[]{1, 2});

        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setVerticalAlignment(Element.ALIGN_TOP);
        try (InputStream imageStream = getClass().getClassLoader().getResourceAsStream("images/IMG-20250205-WA0007.jpg")) {
            if (imageStream == null) throw new FileNotFoundException("Logo not found");
            Image logo = Image.getInstance(ImageIO.read(imageStream), null);
            logo.scaleToFit(150, 150);  // Increased size
            logo.setAlignment(Image.ALIGN_LEFT);
            logoCell.addElement(logo);
        }
        headerTable.addCell(logoCell);

//        PdfPCell addressCell = getRightCell("Eventico B.V.\nRomeinenweg 110-E\n5349AL Oss", regularFont);
//        headerTable.addCell(addressCell);

        PdfPCell addressCell = getRightCell("\n \nINKOOPOPDRACHT", reallyBigBoldFont);
        headerTable.addCell(addressCell);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);

        // Title
//        Paragraph title = new Paragraph("INKOOPOPDRACHT", bigBoldFont);
//        title.setSpacingBefore(26);
//        title.setAlignment(1);
//        document.add(title);
//        document.add(Chunk.NEWLINE);

        Paragraph add1 = new Paragraph("         Eventico B.V.", boldFont);
        add1.setSpacingBefore(20);
        Paragraph add2 = new Paragraph("   Romeinenweg 110-E", boldFont);
        Paragraph add3 = new Paragraph("          5349AL Oss", boldFont);

//        title.setSpacingBefore(26);
//        title.setAlignment(1);
        document.add(add1);
        document.add(add2);
        document.add(add3);
        document.add(Chunk.NEWLINE);

        // Metadata table
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(40);
        infoTable.setSpacingAfter(8);
        infoTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        infoTable.addCell(getLeftCell("Crediteurnummer", boldFont));
        infoTable.addCell(getLeftCell("0", regularFont));
        infoTable.addCell(getLeftCell("Inkoopopdracht", boldFont));
        infoTable.addCell(getLeftCell("0", regularFont));
        infoTable.addCell(getLeftCell("Datum", boldFont));
        infoTable.addCell(getLeftCell(invoice.getInvoiceDate() + "", regularFont));
        infoTable.addCell(getLeftCell("Weeknummer", boldFont));
        infoTable.addCell(getLeftCell("Inkooporder week 2025-05\nWeek 20", regularFont));

        document.add(infoTable);

        // Items table
        PdfPTable items = new PdfPTable(6);
        items.setWidthPercentage(100);
//        items.setWidths(new float[]{2, 1.2f, 1.2f, 2.2f, 2.5f, 1.5f, 1.8f});
        items.setWidths(new float[]{2, 1.2f, 1.2f, 2.5f, 1.5f, 1.8f});

        items.addCell(getHeaderCell("Leverdatum"));
        items.addCell(getHeaderCell("Aantal"));
        items.addCell(getHeaderCell("Tarief"));
//        items.addCell(getHeaderCell("Artikel Omschr."));
        items.addCell(getHeaderCell("Omschrijving"));
        items.addCell(getHeaderCell("Projectnr."));
        items.addCell(getHeaderCell("Totaal ex BTW"));

        items.addCell(getBodyCell("" + invoice.getInvoiceDate()));
        items.addCell(getBodyCell("" + invoice.getHoursWorked()));
        String formattedRate = "€" + String.valueOf(invoice.getSpRate()).replace('.', ',');
        items.addCell(getBodyCell(formattedRate));
//        items.addCell(getBodyCell(""+invoice.getJob().getRequiredSkills().stream().findFirst().map(Skill::getName).orElse("")));
        items.addCell(getBodyCell("" + invoice.getServiceProvider().getFirstName() + " " + invoice.getServiceProvider().getLastName()));
        items.addCell(getBodyCell("INF" + invoice.getJob().getId()));
        double total = invoice.getHoursWorked() * invoice.getSpRate();
        items.addCell(getBodyCell("€ " + String.format("%.2f", total).replace('.', ',')));

        document.add(items);

        // Total section
        document.add(Chunk.NEWLINE);
        PdfPTable totals = new PdfPTable(2);
        totals.setWidthPercentage(30);
        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totals.setWidths(new float[]{2, 1});
        if (invoice.getExtraCost() == null) {
            invoice.setExtraCost(0.00);
        }
        totals.addCell(getLeftCell("Valuta", regularFont));
        totals.addCell(getRightCell("Euro", regularFont));
        totals.addCell(getLeftCell("Subtotaal", regularFont));
        double employeeTotal = ((invoice.getHoursWorked() * invoice.getSpRate()) + invoice.getExtraCost());
        double btwAmount = employeeTotal * 0.21;
        totals.addCell(getRightCell("€ " + String.format("%.2f", employeeTotal).replace('.', ','), regularFont));
        totals.addCell(getLeftCell("21% BTW bedrag", regularFont));
        totals.addCell(getRightCell("€ " + String.format("%.2f", btwAmount).replace('.', ','), regularFont));
        totals.addCell(getLeftCell("Factuurbedrag", boldFont));
        totals.addCell(getRightCell("€ " + String.format("%.2f", (btwAmount + employeeTotal)).replace('.', ','), regularFont));

        document.add(totals);

        // Footer text
        document.add(Chunk.NEWLINE);
        Paragraph footer = new Paragraph();
        footer.add(new Chunk("Facturering\n", boldFont));
        footer.add(new Chunk(
                "De factuur dient uitsluitend te worden aangeleverd in PDF-formaat en mag alleen op deze inkoopopdracht betrekking hebben. " +
                        "Facturen moeten worden gestuurd naar ap-nl@eventico.nl met vermelding van het inkoopopdrachtnummer. Zonder dit nummer kan de factuur niet in behandeling worden genomen.\n\n",
                regularFont));
//        footer.add(new Chunk("ATR Infra-Services | KVK: 82017455 | BTW: NL862354562B01 | IBAN: NL94 ABNA 0838 8774 76 | BIC: ABNANL2A", regularFont));
        footer.add(new Chunk("ATR Infra-Services | KVK: 0 | BTW: 0 | IBAN: 0 | BIC: 0", regularFont));


        document.add(footer);

        document.close();

        return outputStream.toByteArray();
    }


    public byte[] generateInvoicePdf(Invoice invoice) throws Exception {
        double invTotal = invoice.getInvoiceLines().stream()
                .mapToDouble(i -> i.getPrice() * i.getAantal()) // Map to double for each invoice line
                .sum();
        double invTotalTax = invTotal * 0.21;

        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Fonts
        Font bigBoldFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
        Font bold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font normal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Font smallBold = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);

        PdfPCell spacer = new PdfPCell();
        spacer.setBorder(Rectangle.NO_BORDER);

        // === HEADER WITH LOGO + COMPANY INFO ===
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidths(new int[]{3, 4, 4});
        headerTable.setWidthPercentage(100);

        // Logo cell (larger logo)
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setVerticalAlignment(Element.ALIGN_TOP);
        try (InputStream imageStream = getClass().getClassLoader().getResourceAsStream("images/IMG-20250205-WA0007.jpg")) {
            if (imageStream == null) throw new FileNotFoundException("Logo not found");
            Image logo = Image.getInstance(ImageIO.read(imageStream), null);
            logo.scaleToFit(150, 150);  // Increased size
            logo.setAlignment(Image.ALIGN_LEFT);
            logoCell.addElement(logo);
        }

        // Company info
        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(Rectangle.NO_BORDER);
        companyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Paragraph companyInfo1 = new Paragraph("Eventico B.V.", bold);
        Paragraph companyInfo2 = new Paragraph("Gewandeweg 39\n5345HM Oss\nT: 0648908818\nE: administratie@eventico.nl\nI: eventico.nl", normal);
        companyInfo1.setAlignment(Element.ALIGN_LEFT);
        companyInfo2.setAlignment(Element.ALIGN_LEFT);
        companyCell.addElement(companyInfo1);
        companyCell.addElement(companyInfo2);

        headerTable.addCell(logoCell);
        headerTable.addCell(spacer);
        headerTable.addCell(companyCell);
        document.add(headerTable);

        document.add(Chunk.NEWLINE);

        // === INVOICE TO ===
        Paragraph paragraph = new Paragraph("Factuur", bigBoldFont);
        paragraph.setSpacingAfter(15f);
        document.add(paragraph);

        PdfPTable details = new PdfPTable(3);
        details.setWidths(new int[]{3, 4, 4});
        details.setWidthPercentage(100);

        PdfPCell detailsCell = new PdfPCell();
        detailsCell.setBorder(Rectangle.NO_BORDER);
//        detailsCell.setHorizontalAlignment(Element.ALIGN_LEFT);

        Paragraph details1 = new Paragraph("Story Event Engineering", bold);
        Paragraph details2 = new Paragraph("Zandkamp", normal);
        Paragraph details3 = new Paragraph("6626KV Alphen", normal);

        details1.setAlignment(Element.ALIGN_LEFT);
        details2.setAlignment(Element.ALIGN_LEFT);
        details3.setAlignment(Element.ALIGN_LEFT);

        detailsCell.addElement(details1);
        detailsCell.addElement(details2);
        detailsCell.addElement(details3);


        PdfPCell detailsCel2 = new PdfPCell();
        detailsCel2.setBorder(Rectangle.NO_BORDER);
//        detailsCel2.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Paragraph detailsC1 = new Paragraph("IBAN:    NL90 KNAB 0510 4223 73", normal);
        Paragraph detailsC2 = new Paragraph("Btw-nr:  NL865707753801", normal);
        Paragraph detailsC3 = new Paragraph("KvK:      91567870", normal);

        detailsC1.setAlignment(Element.ALIGN_LEFT);
        detailsC2.setAlignment(Element.ALIGN_LEFT);
        detailsC3.setAlignment(Element.ALIGN_LEFT);

        detailsCel2.addElement(detailsC1);
        detailsCel2.addElement(detailsC2);
        detailsCel2.addElement(detailsC3);

        details.addCell(detailsCell);
        details.addCell(spacer);
        details.addCell(detailsCel2);

        document.add(details);
        document.add(Chunk.NEWLINE);

        // === PAYMENT INSTRUCTIONS RIGHT ===

        PdfPTable paymentMainTable = new PdfPTable(3);
        paymentMainTable.setWidths(new int[]{5, 2, 4});
        paymentMainTable.setWidthPercentage(100);


        PdfPTable paymentTable = new PdfPTable(2);
        paymentTable.setWidths(new float[]{3, 5});
        paymentTable.setWidthPercentage(5);
        paymentTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        paymentTable.addCell(getLeftCell("Te betalen:", normal));
        paymentTable.addCell(getLeftCell("€ " + (invTotal + invTotalTax) + " (voor " + invoice.getInvoiceDate() + ")", bold));
        paymentTable.addCell(getLeftCell("Naar IBAN:", normal));
        paymentTable.addCell(getLeftCell("NL90 KNAB 0510 4223 73", bold));
        paymentTable.addCell(getLeftCell("Op naam van:", normal));
        paymentTable.addCell(getLeftCell("Eventico B.V.", bold));
        paymentTable.addCell(getLeftCell("Omschrijving:", normal));
        paymentTable.addCell(getLeftCell("Factuur " + invoice.getInvoiceNumber(), bold));


        PdfPTable metaTable = new PdfPTable(2);
        metaTable.setWidths(new float[]{4, 3});
        metaTable.setWidthPercentage(10);
        metaTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        metaTable.addCell(getLeftCell("Factuurnummer:", bold));
        metaTable.addCell(getLeftCell("" + invoice.getInvoiceNumber(), normal));
        metaTable.addCell(getLeftCell("Factuurdatum:", bold));
        metaTable.addCell(getLeftCell("" + invoice.getInvoiceDate(), normal));
        metaTable.addCell(getLeftCell("Betalingstermijn:", bold));
        metaTable.addCell(getLeftCell(invoice.getDagen() != null ? invoice.getDagen() : 0 + " dagen", normal));
        PdfPCell spacer2 = new PdfPCell(new Phrase(" "));
        spacer2.setColspan(2);
        spacer2.setBorder(Rectangle.NO_BORDER);
        spacer2.setFixedHeight(10f);
        metaTable.addCell(spacer2);
        metaTable.addCell(getLeftCell("Klantnummer:", bold));
        metaTable.addCell(getLeftCell("0", normal));
        metaTable.addCell(getLeftCell("Leverdatum:", bold));
        metaTable.addCell(getLeftCell(invoice.getInvoiceDate() + "", normal));

        PdfPCell cell = new PdfPCell(paymentTable);
        cell.setBorder(Rectangle.NO_BORDER);
        paymentMainTable.addCell(cell);

        paymentMainTable.addCell(spacer);

        PdfPCell cell2 = new PdfPCell(metaTable);
        cell2.setBorder(Rectangle.NO_BORDER);
        paymentMainTable.addCell(cell2);

        paymentMainTable.addCell(cell2);

        document.add(paymentMainTable);

        document.add(Chunk.NEWLINE);

        // === META INFO TABLE RIGHT ===

        document.add(Chunk.NEWLINE);

        // === ITEMS TABLE ===
        PdfPTable items = new PdfPTable(4);
        items.setWidths(new float[]{6, 1, 2, 2});
        items.setWidthPercentage(100);
        items.addCell(getHeaderCell("Omschrijving"));
        items.addCell(getHeaderCell("Aantal"));
        items.addCell(getHeaderCell("Prijs"));
        items.addCell(getHeaderCell("Totaal"));

        invoice.getInvoiceLines().forEach(il -> {
            items.addCell(getBodyCell(il.getOmschrijving()));
            items.addCell(getBodyCell(il.getAantal() + ""));
            items.addCell(getBodyCell(il.getPrice() + ""));
            items.addCell(getBodyCell(il.getPrice() * il.getAantal() + ""));
        });

        document.add(items);
        document.add(Chunk.NEWLINE);

        // === FOOTER SECTION: BTW GROUNDSLAG ===
        PdfPTable btwTable = new PdfPTable(2);
        btwTable.setWidths(new float[]{3, 2});
        btwTable.setWidthPercentage(40);
        btwTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        btwTable.addCell(getRightCell("Btw grondslag:", smallBold));
        btwTable.addCell(getRightCell("€ " + invTotal, normal));
        btwTable.addCell(getRightCell("21% Btw:", smallBold));
        btwTable.addCell(getRightCell("€ " + invTotalTax, normal));
        btwTable.addCell(getRightCell("Totaal inclusief btw:", smallBold));
        btwTable.addCell(getRightCell("€ " + (invTotal + invTotalTax), smallBold));
        document.add(btwTable);

        document.close();
        return outputStream.toByteArray();
    }

// === HELPER METHODS ===

    private PdfPCell getRightCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(3);
        return cell;
    }

    private PdfPCell getLeftCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(3);
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
        Optional<Invoice> spInvoice = Optional.empty();
        if (existingInvoice.isPresent()) {
            List<Invoice> presentInvoices = existingInvoice.get();
            spInvoice = presentInvoices
                    .stream()
                    .filter(inv -> inv.getInvoiceType().equals(InvoiceType.PURCHASE) && inv.getServiceProvider().getId() == serviceProviderId)
                    .findFirst();
            if (spInvoice.isPresent()) {
                if (!isSpReview) {
                    return spInvoice.get();
                }
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
                if (invoiceDto.getFiles() != null) {
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
                            savedPaths.add(filename);
                        }
                    }
                    invoice.setFilePath(savedPaths);
                }
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
            invoice.setCreatedDateTime(LocalDateTime.now());
            double hoursWorked = ChronoUnit.MINUTES.between(job.getStartTime(), job.getEndTime()) / 60.0;
            if (job.getClient().getRateType().equals(RateType.HOURLY)) {
                invoice.setHoursWorked(hoursWorked);
            } else {
                invoice.setHoursWorked(1);
            }

            LocalTime startTime = invoice.getStartTime();
            LocalTime endTime = invoice.getEndTime();

            Duration duration = Duration.between(startTime, endTime);
            double workedTime = duration.toMinutes() / 60.0;
            workedTime = Math.round(workedTime * 100.0) / 100.0;
            invoice.setWorkedtime(workedTime);

            invoice.setInvoiceDate(LocalDate.now());
            invoice.setDayOrHour(job.getClient().getRateType().equals(RateType.HOURLY) ? "Hour" : "Day");
            invoice.setSpRate(UtilFunctions.studentsRateBasedOnEventTypeAndJobTime(job, sp));
            if (isSpReview && spInvoice.isPresent()) {
                invoice.setId(spInvoice.get().getId());
            }
            return this.invoiceRepo.save(invoice);
        } else {
            return null;
        }
    }


    @Override
    public void postReviewFormServiceProvider(String token, InvoiceDto invoiceDto) throws Exception {
        String serviceProviderId = this.jwtTokenHelper.getSpIdFromToken(token);

        //Long serviceProviderId = invoiceDto.getServiceProvider().getId();
        if (!isReviewedServiceProviderReviewForm(token, Long.parseLong(serviceProviderId))) {
            Long jobId = jwtTokenHelper.getJobIdFromToken(token);
            createPurchaseOrder(jobId, Long.parseLong(serviceProviderId), invoiceDto, true);
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
