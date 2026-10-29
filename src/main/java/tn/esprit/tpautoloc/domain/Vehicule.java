package tn.esprit.tpautoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    private String immatriculation;
    private String marque;
    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    @ManyToOne
    @JsonBackReference("agence-vehicule")
    private Agence agence;

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Maintenance> maintenances = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Equipement> equipements = new ArrayList<>();
}