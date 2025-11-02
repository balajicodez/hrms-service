package com.simplerp.hrms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simplerp.hrms.service.JpaJsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key

    // Self-join for parent organization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_organization_id")
    private Organization parentOrganization;
    private String name;               // Organization name
    private String registrationNo;     // Optional legal registration
    private String gstn;               // Optional
    private String pan;                // Optional
    private String contact;            // Primary mobile number
    private String fax;                // Optional
    private String email;              // Contact email
    private String website;            // Organization website
    private String status;             // Active / Inactive

    /**
     * Address details stored as JSON in DB.
     * Example:
     * {
     * "address1": "123 Street",
     * "address2": "Suite 45",
     * "addressType": "Permanent",
     * "pinZip": "500001",
     * "city": "Hyderabad",
     * "region": "Telangana",
     * "country": "India"
     * }
     */
    @Lob
    @Convert(converter = JpaJsonConverter.class)
    @Column(columnDefinition = "TEXT") // for H2; PostgreSQL will still accept this
    private Map<String, String> address;
}

