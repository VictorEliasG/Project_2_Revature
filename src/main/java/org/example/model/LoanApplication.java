package org.example.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_applications", schema = "loans")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_applications_id")
    private Long id;
    @Column(name = "principal_balance", precision = 10, scale = 2)
    private BigDecimal principalBalance;
    @Column(name = "interest", precision = 5, scale = 2)
    private BigDecimal interest;
    @Column(name = "term_length")
    private Integer termLength;
    @Column(name = "total_balance", precision = 10, scale = 2)
    private BigDecimal totalBalance;

    @ManyToOne
    @JoinColumn(name = "application_statuses_id")
    private ApplicationStatus applicationStatus;
    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;
    @ManyToOne
    @JoinColumn(name = "user_profiles_id")
    private UserProfile userProfile;
}
