package com.rentmen.app.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
		name = "users"
)
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	@Column(
			name = "id"
	)
	private long id;
	private String firstName;
	private String lastName;
	private String businessRegistration;
	@Enumerated(EnumType.STRING)
	@Column(
			name = "rate_type"
	)
	private RateType rateType;
	@Column(
			name = "time_based_rate"
	)
	private Boolean timeBasedRate;
	@Column(
			name = "email",
			nullable = false,
			length = 100,
			unique = true
	)
	private String email;
	private String VAT;
	private Double discussedRate;
	private Double dayRate;
	private Double nightRate;
	private Double weekendRate;
	private String password;
	private Integer depId;
	private Boolean active;
	private Boolean status;
	private String agreement;
	@Lob
	private String about;
	@ManyToMany(
			cascade = {CascadeType.ALL},
			fetch = FetchType.EAGER
	)
	@JoinTable(
			name = "user_role",
			joinColumns = {@JoinColumn(
					name = "user_id",
					referencedColumnName = "id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "role_id",
					referencedColumnName = "id"
			)}
	)
	private Set<Role> roles = new HashSet();
	private String profileImage;

	public User() {
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = (List)this.roles.stream().map((role) -> {
			return new SimpleGrantedAuthority(role.getName());
		}).collect(Collectors.toList());
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getProfileImage() {
		return this.profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getUsername() {
		return this.firstName;
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

	public String getAgreement() {
		return this.agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public RateType getRateType() {
		return this.rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

	public Boolean getTimeBasedRate() {
		return this.timeBasedRate;
	}

	public void setTimeBasedRate(Boolean timeBasedRate) {
		this.timeBasedRate = timeBasedRate;
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

	public static enum RateType {
		DAY("DAY"),
		HOURLY("HOURLY");

		private final String value;

		private RateType(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return this.value;
		}

		@JsonCreator
		public static RateType fromString(String key) {
			if (key == null) {
				return null;
			} else {
				switch (key.toUpperCase()) {
					case "DAY":
						return DAY;
					case "HOURLY":
						return HOURLY;
					default:
						throw new IllegalArgumentException("Unknown value: " + key);
				}
			}
		}
	}
}
