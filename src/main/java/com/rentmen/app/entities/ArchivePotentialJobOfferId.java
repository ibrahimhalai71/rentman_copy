package com.rentmen.app.entities;

import java.io.Serializable;
import java.util.Objects;

public class ArchivePotentialJobOfferId implements Serializable {
	private long job;
	private long serviceProvider;

	public long getJob() {
		return this.job;
	}

	public long getServiceProvider() {
		return this.serviceProvider;
	}

	public void setJob(long job) {
		this.job = job;
	}

	public void setServiceProvider(long serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public ArchivePotentialJobOfferId() {
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.job, this.serviceProvider});
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			ArchivePotentialJobOfferId other = (ArchivePotentialJobOfferId)obj;
			return this.job == other.job && this.serviceProvider == other.serviceProvider;
		}
	}

	public ArchivePotentialJobOfferId(long job, long serviceProvider) {
		this.job = job;
		this.serviceProvider = serviceProvider;
	}
}
