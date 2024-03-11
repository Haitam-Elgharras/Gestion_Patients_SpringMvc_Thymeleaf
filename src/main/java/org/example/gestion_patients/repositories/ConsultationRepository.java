package org.example.gestion_patients.repositories;


import org.example.gestion_patients.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
