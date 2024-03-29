package org.example.gestion_patients;


import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.gestion_patients.entities.Patient;
import org.example.gestion_patients.entities.RendezVous;
import org.example.gestion_patients.repositories.PatientRepository;
import org.example.gestion_patients.repositories.RendezVousRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.example.gestion_patients.entities.StatusRDV.*;

@SpringBootApplication
@RequiredArgsConstructor
public class HospitalJeeSpringMvcApplication implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final RendezVousRepository rendezVousRepository;

    @Setter
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(HospitalJeeSpringMvcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // create some patients and save them to the database
        Patient patient1 = new Patient(null, "ahmed","ahmed", new Date(), false,10, null);
        Patient patient2 = new Patient(null, "youssef","ahmed", new Date(), false,20, null);
        Patient patient3 = new Patient(null, "mohamed","ahmed", new Date(), false,20, null);
        Patient patient4 = new Patient(null, "fatima","ahmed", new Date(), false,60, null);

        for (int i =0;i<50;i++){
            Patient patient = new Patient(null, "ahmed"+i,"mohamed"+i, new Date(), false,i+10, null);
            patientRepository.save(patient);
            // reference this patient into some rendezVous
            RendezVous rendezVous = new RendezVous(null, LocalDateTime.of(2022, 12, 31, 12, 30, 59), PENDING, patient, null, null);
            RendezVous rendezVous2 = new RendezVous(null, LocalDateTime.of(2022, 1, 1, 10, 0, 59), CANCELED, patient, null, null);
            RendezVous rendezVous3 = new RendezVous(null, LocalDateTime.of(2023, 6, 17, 23, 59, 59), DONE, patient, null, null);

            rendezVousRepository.saveAll(List.of(rendezVous, rendezVous2, rendezVous3));
        }

        // using builder
        Patient patient5 = Patient.builder()
                                   .nom("Imad")
                .prenom("Imad")
                                    .dateNaissance(new Date())
                                     .malade(false).score(60)
                                     .build();

        patientRepository.saveAll(List.of(patient1, patient2, patient3, patient4,patient5));

    }

//    @Bean for jdbc authentication
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager manager) {
        return args -> {
            if (!manager.userExists("admin2@gmail.com")) {
                manager.createUser(
                        User.withUsername("admin2@gmail.com")
                                .password(passwordEncoder.encode("1234"))
                                .roles("ADMIN","USER")
                                .build()
                );
                manager.createUser(
                        User.withUsername("user22@gmail.com")
                                .password(passwordEncoder.encode("1234"))
                                .roles("USER")
                                .build()
                );
                manager.createUser(
                        User.withUsername("user11@gmail.com")
                                .password(passwordEncoder.encode("1234"))
                                .roles("USER")
                                .build()
                );

            }
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
