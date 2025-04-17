package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mailing_addresses", schema = "loans")
public class MailingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mailing_addresses_id")
    private Long id;

    @Column(name = "street", length = 45)
    private String street;

    @Column(name = "city", length = 45)
    private String city;

    @Column(name = "state", length = 45)
    private String state;

    @Column(name = "zip", length = 45)
    private String zip;

    @Column(name = "country", length = 45)
    private String country;

    @OneToOne(mappedBy = "mailingAddress")
    @JsonIgnore
    private UserProfile userProfile = new UserProfile();

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

}
