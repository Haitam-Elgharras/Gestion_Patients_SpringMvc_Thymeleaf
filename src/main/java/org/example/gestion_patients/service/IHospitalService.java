package org.example.gestion_patients.service;


import org.example.gestion_patients.entities.Consultation;
import org.example.gestion_patients.entities.Medecin;
import org.example.gestion_patients.entities.Patient;
import org.example.gestion_patients.entities.RendezVous;

import java.util.List;

public interface IHospitalService {
    Patient savePatient(Patient p);
    Medecin saveMedecin(Medecin m);
    RendezVous saveRendezVous(RendezVous r);
    Consultation saveConsultation(Consultation c);

    Medecin findMedecinByName(String name);

    List<Patient> findAllPatients();
}
