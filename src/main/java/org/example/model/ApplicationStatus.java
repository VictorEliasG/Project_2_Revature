package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_statuses", schema = "loans")
public class ApplicationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_statuses_id")
    private int id;

    @Column(name = "application_statuses", length = 10, unique = true)
    private String status;

    @Column(name = "description", length = 100)
    private String description;

    public String getApplicationStatus() {
        return this.status;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.status = applicationStatus;
    }

}