package com.rentmen.app.entities;

import java.io.Serializable;
import java.util.Objects;

public class PotentialJobOfferId implements Serializable {
	public long getJob() {
		return job;
	}
	public long getServiceProvider() {
		return serviceProvider;
	}
	public void setJob(long job) {
		this.job = job;
	}
	public void setServiceProvider(long serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	private long job;
	private long serviceProvider;
	public PotentialJobOfferId() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(job, serviceProvider);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PotentialJobOfferId other = (PotentialJobOfferId) obj;
		return job == other.job && serviceProvider == other.serviceProvider;
	}
	public PotentialJobOfferId(long job, long serviceProvider) {
		super();
		this.job = job;
		this.serviceProvider = serviceProvider;
	}

}
