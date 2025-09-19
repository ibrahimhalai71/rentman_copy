
package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentmen.app.entities.User;
import com.rentmen.app.utils.AvailabilityOptions;
import com.rentmen.app.utils.ClientEventRateType;
import com.rentmen.app.utils.DriversLicense;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDto {
	private long id;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("business_registration")
	private String businessRegistration;
	private @NotEmpty @Size(
			min = 3,
			message = "Username must be at least 3 characters"
	) String email;
	private String VAT;
	private @NotEmpty @Size(
			min = 4,
			max = 16,
			message = "Password should be between 4-16 characters"
	) String password;
	private String about;
	private Integer depId;
	private Boolean active;
	private Boolean status;
	private String pin;
	@JsonProperty("profile_image")
	private String profileImage;
	private String address;
	@JsonProperty("contact_person")
	private String contactPerson;
	@JsonProperty("phone_number")
	private String phoneNumber;
	@JsonProperty("phone_number_planning")
	private String phoneNumberPlanning;
	@JsonProperty("invoice_mail")
	private String invoiceMail;
	@JsonProperty("sector")
	private String sector;
	@JsonProperty("kilometer_rate")
	private Double kilometerRate;

	private Boolean timeBasedRate;
	@JsonProperty("discussed_rate_type")
	private User.RateType rateType;
	private String accessLevel;
	private Boolean availabilityStatus;
	private Integer rating;
	private String contact;
	private Set<SkillDto> skills = new HashSet();
	@JsonProperty("availability_start_date")
	private LocalDate availabilityStartDate;
	@JsonProperty("availability_end_date")
	private LocalDate availabilityEndDate;
	@JsonProperty("unavailable_start_date")
	private LocalDate unavailableStartDate;
	@JsonProperty("unavailable_end_date")
	private LocalDate unavailableEndDate;
	@JsonProperty("unknown_start_date")
	private LocalDate unknownStartDate;
	@JsonProperty("unknown_end_date")
	private LocalDate unknownEndDate;
	@JsonProperty("availability_options")
	private AvailabilityOptions availabilityOptions;
	@JsonProperty("drivers_license")
	private DriversLicense driversLicense;
	private String agreement;
	@JsonProperty("date_of_birth")
	private LocalDate dateOfBirth;
	@JsonProperty("availability_dates")
	private List<LocalDate> availabilityDates;
	@JsonProperty("unavailability_dates")
	private List<LocalDate> unavailabilityDates;
	@JsonProperty("unknown_dates")
	private List<LocalDate> unknownDates;

	@JsonProperty("minimum_payment_hour")
	private Double minimumPaymentHour;
	@JsonProperty("client_event_rate_type")
	@Enumerated(EnumType.STRING)
	private ClientEventRateType clientEventRateType;
	@JsonProperty("discussed_rate")
	private Double discussedRate;
	@JsonProperty("discussed_rate_rw")
	private Double discussedRateRW;
	@JsonProperty("hour_rate")
	private Double hourRate;
	@JsonProperty("hour_rate_rw")
	private Double hourRateRW;
	@JsonProperty("night_rate")
	private Double nightRate;
	@JsonProperty("night_rate_rw")
	private Double nightRateRW;
	@JsonProperty("weekend_rate")
	private Double weekendRate;
	@JsonProperty("weekend_rate_rw")
	private Double weekendRateRW;
	@JsonProperty("day_rate")
	private Double dayRate;


	public UserDto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getDepId() {
		return this.depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Double getDiscussedRateRW() {
		return discussedRateRW;
	}

	public void setDiscussedRateRW(Double discussedRateRW) {
		this.discussedRateRW = discussedRateRW;
	}

	public Double getHourRate() {
		return hourRate;
	}

	public void setHourRate(Double hourRate) {
		this.hourRate = hourRate;
	}

	public Double getHourRateRW() {
		return hourRateRW;
	}

	public void setHourRateRW(Double hourRateRW) {
		this.hourRateRW = hourRateRW;
	}

	public Double getNightRateRW() {
		return nightRateRW;
	}

	public void setNightRateRW(Double nightRateRW) {
		this.nightRateRW = nightRateRW;
	}

	public Double getWeekendRateRW() {
		return weekendRateRW;
	}

	public void setWeekendRateRW(Double weekendRateRW) {
		this.weekendRateRW = weekendRateRW;
	}

	public String getProfileImage() {
		return this.profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getAccessLevel() {
		return this.accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Boolean getAvailabilityStatus() {
		return this.availabilityStatus;
	}

	public void setAvailabilityStatus(Boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Set<SkillDto> getSkills() {
		return this.skills;
	}

	public void setSkills(Set<SkillDto> skills) {
		this.skills = skills;
	}

	public LocalDate getAvailabilityStartDate() {
		return this.availabilityStartDate;
	}

	public LocalDate getAvailabilityEndDate() {
		return this.availabilityEndDate;
	}

	public AvailabilityOptions getAvailabilityOptions() {
		return this.availabilityOptions;
	}

	public void setAvailabilityStartDate(LocalDate availabilityStartDate) {
		this.availabilityStartDate = availabilityStartDate;
	}

	public void setAvailabilityEndDate(LocalDate availabilityEndDate) {
		this.availabilityEndDate = availabilityEndDate;
	}

	public void setAvailabilityOptions(AvailabilityOptions availabilityOptions) {
		this.availabilityOptions = availabilityOptions;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getBusinessRegistration() {
		return this.businessRegistration;
	}

	public String getVAT() {
		return this.VAT;
	}

	public Double getDiscussedRate() {
		return this.discussedRate;
	}

	public String getAddress() {
		return this.address;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getPhoneNumberPlanning() {
		return this.phoneNumberPlanning;
	}

	public String getInvoiceMail() {
		return this.invoiceMail;
	}

	public String getSector() {
		return this.sector;
	}

	public Double getKilometerRate() {
		return this.kilometerRate;
	}

	public DriversLicense getDriversLicense() {
		return this.driversLicense;
	}

	public String getAgreement() {
		return this.agreement;
	}

	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBusinessRegistration(String businessRegistration) {
		this.businessRegistration = businessRegistration;
	}

	public void setVAT(String vAT) {
		this.VAT = vAT;
	}

	public void setDiscussedRate(Double discussedRate) {
		this.discussedRate = discussedRate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneNumberPlanning(String phoneNumberPlanning) {
		this.phoneNumberPlanning = phoneNumberPlanning;
	}

	public void setInvoiceMail(String invoiceMail) {
		this.invoiceMail = invoiceMail;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public void setKilometerRate(Double kilometerRate) {
		this.kilometerRate = kilometerRate;
	}

	public void setDriversLicense(DriversLicense driversLicense) {
		this.driversLicense = driversLicense;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Double getDayRate() {
		return this.dayRate;
	}

	public void setDayRate(Double dayRate) {
		this.dayRate = dayRate;
	}

	public Double getNightRate() {
		return this.nightRate;
	}

	public void setNightRate(Double nightRate) {
		this.nightRate = nightRate;
	}

	public Double getWeekendRate() {
		return this.weekendRate;
	}

	public void setWeekendRate(Double weekendRate) {
		this.weekendRate = weekendRate;
	}

	public Boolean getTimeBasedRate() {
		return this.timeBasedRate;
	}

	public void setTimeBasedRate(Boolean timeBasedRate) {
		this.timeBasedRate = timeBasedRate;
	}

	public User.RateType getRateType() {
		return this.rateType;
	}

	public void setRateType(User.RateType rateType) {
		this.rateType = rateType;
	}

	public LocalDate getUnavailableStartDate() {
		return unavailableStartDate;
	}

	public void setUnavailableStartDate(LocalDate unavailableStartDate) {
		this.unavailableStartDate = unavailableStartDate;
	}

	public LocalDate getUnavailableEndDate() {
		return unavailableEndDate;
	}

	public void setUnavailableEndDate(LocalDate unavailableEndDate) {
		this.unavailableEndDate = unavailableEndDate;
	}

	public LocalDate getUnknownStartDate() {
		return unknownStartDate;
	}

	public void setUnknownStartDate(LocalDate unknownStartDate) {
		this.unknownStartDate = unknownStartDate;
	}

	public LocalDate getUnknownEndDate() {
		return unknownEndDate;
	}

	public void setUnknownEndDate(LocalDate unknownEndDate) {
		this.unknownEndDate = unknownEndDate;
	}

	public List<LocalDate> getAvailabilityDates() {
		return availabilityDates;
	}

	public void setAvailabilityDates(List<LocalDate> availabilityDates) {
		this.availabilityDates = availabilityDates;
	}

	public List<LocalDate> getUnavailabilityDates() {
		return unavailabilityDates;
	}

	public void setUnavailabilityDates(List<LocalDate> unavailabilityDates) {
		this.unavailabilityDates = unavailabilityDates;
	}

	public Double getMinimumPaymentHour() {
		return minimumPaymentHour;
	}

	public void setMinimumPaymentHour(Double minimumPaymentHour) {
		this.minimumPaymentHour = minimumPaymentHour;
	}

	public ClientEventRateType getClientEventRateType() {
		return clientEventRateType;
	}

	public void setClientEventRateType(ClientEventRateType clientEventRateType) {
		this.clientEventRateType = clientEventRateType;
	}

	public List<LocalDate> getUnknownDates() {
		return unknownDates;
	}

	public void setUnknownDates(List<LocalDate> unknownDates) {
		this.unknownDates = unknownDates;
	}
}
