
package org.example.gestion_patients.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@Entity
//@Data // will generate getters and setters
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 4, max = 20, message = "le nom doit etre entre 4 et 20 caractere")
    private String nom;

    @NotBlank
    @Size(min = 4, max = 20, message = "le prenom doit etre entre 4 et 20 caractere")
    private String prenom;

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private boolean malade;

    @Min(value = 10 , message = "le score doit etre superieur a 10")
    private int score;

    @OneToMany(mappedBy = "patient",fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;

}
