package com.skincare.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Appointment Entity
 * Manages dermatologist appointment bookings
 */
@Entity
@Table(name = "appointments", indexes = {
    @Index(name = "idx_user_appointment", columnList = "user_id"),
    @Index(name = "idx_appointment_date", columnList = "appointment_date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "doctor_name", nullable = false, length = 100)
    private String doctorName;

    @Column(name = "doctor_specialization", length = 100)
    private String doctorSpecialization;

    @Column(name = "doctor_phone", length = 20)
    private String doctorPhone;

    @Column(name = "doctor_email", length = 100)
    private String doctorEmail;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(length = 255)
    private String location;

    @Column
    private String address;

    @Column(length = 20)
    private Double latitude;

    @Column(length = 20)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "patient_concerns", columnDefinition = "TEXT")
    private String patientConcerns;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Appointment status enum
    public enum AppointmentStatus {
        SCHEDULED,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
        RESCHEDULED
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
