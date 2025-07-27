package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "farmers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String farmLicense;
    
    @Column(nullable = false)
    private String businessLicense;
    
    private String farmName;
    private String farmAddress;
    private LocalDate licenseExpiryDate;
    private String farmDescription;
    private String farmingType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

}