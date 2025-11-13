package com.skincare.repository;

import com.skincare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Appointment Repository
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUserIdOrderByAppointmentDateDesc(Long userId);

    List<Appointment> findByStatus(Appointment.AppointmentStatus status);

    List<Appointment> findByUserIdAndStatus(Long userId, Appointment.AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.appointmentDate >= :today ORDER BY a.appointmentDate ASC")
    List<Appointment> findUpcomingAppointmentsByUserId(
        @Param("userId") Long userId, 
        @Param("today") LocalDate today
    );

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = :date ORDER BY a.appointmentTime ASC")
    List<Appointment> findByAppointmentDate(@Param("date") LocalDate date);

    Long countByUserId(Long userId);

    Long countByStatus(Appointment.AppointmentStatus status);
}
