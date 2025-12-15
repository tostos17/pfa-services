package com.fowobi.dto;

import java.time.LocalDate;

public class AwardIssuanceRequest {

    private String title;
    private String issuer;
    private String description;
    private LocalDate dateIssued;

    public AwardIssuanceRequest() {
    }

    public AwardIssuanceRequest(String title, String issuer, String description, LocalDate dateIssued) {
        this.title = title;
        this.issuer = issuer;
        this.description = description;
        this.dateIssued = dateIssued;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    @Override
    public String toString() {
        return "AwardIssuanceRequest{" +
                "title='" + title + '\'' +
                ", issuer='" + issuer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
