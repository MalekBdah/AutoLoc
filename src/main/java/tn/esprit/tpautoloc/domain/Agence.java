package tn.esprit.tpautoloc.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;
    private String ville;
    private String adresse;
    private String telephone;


    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    @JsonManagedReference("agence-employe")
    private List<Employe> employes;


    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    @JsonManagedReference("agence-vehicule")
    private List<Vehicule> vehicules;

}