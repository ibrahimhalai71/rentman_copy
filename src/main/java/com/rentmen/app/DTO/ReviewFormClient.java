package com.rentmen.app.DTO;

public class ReviewFormClient {
	
	Long serviceProviderId;
	Float rating;
	
	public Long getServiceProviderId() {
		return serviceProviderId;
	}
	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}

}
