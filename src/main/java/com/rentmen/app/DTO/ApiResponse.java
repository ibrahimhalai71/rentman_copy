package com.rentmen.app.DTO;

public class ApiResponse {
    private String message;
    private boolean success;
    private String error;

    public ApiResponse() {
    }

    public ApiResponse(String message, String error, boolean success) {
        this.message = message;
        this.success = success;
        this.error = error;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
    
    
}
