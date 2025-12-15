package com.fowobi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String issuer;
    private String description;
    private LocalDate dateReceived;

    public Award() {
    }

    public Award(long id, String title, String issuer, String description, LocalDate dateReceived) {
        this.id = id;
        this.title = title;
        this.issuer = issuer;
        this.description = description;
        this.dateReceived = dateReceived;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return dateReceived;
    }

    public void setDate(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    @Override
    public String toString() {
        return "Award{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", issuer='" + issuer + '\'' +
                ", description='" + description + '\'' +
                ", dateReceived=" + dateReceived +
                '}';
    }
}

