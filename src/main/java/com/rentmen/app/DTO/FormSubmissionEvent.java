package com.rentmen.app.DTO;

public class FormSubmissionEvent {
    private String token;
    private InvoiceDto form;

    public FormSubmissionEvent() {
    }

    public FormSubmissionEvent(String token, InvoiceDto form) {
        this.token = token;
        this.form = form;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public InvoiceDto getForm() {
        return form;
    }

    public void setForm(InvoiceDto form) {
        this.form = form;
    }
}
