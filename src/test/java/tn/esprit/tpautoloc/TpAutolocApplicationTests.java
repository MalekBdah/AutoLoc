package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Vehicule;

import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;


import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.service.IVehiculeService;

import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Test
    void contextLoads() {
        assertNotNull(agenceRepository);
        assertNotNull(clientRepository);
    }



    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    @Test
    void testAgenceService() {

        // 1. Création
        Agence agence = new Agence();
        agence.setNom("Agence Ariana");
        agence.setVille("Ariana");
        agence.setAdresse("Centre Ariana");
        agence.setTelephone("70111222");

        // 2. Enregistrement
        Agence saved = agenceService.create(agence);

        System.out.println("Agence enregistrée : " + saved);

        assertNotNull(saved);
        assertNotNull(saved.getIdAgence());

        Long id = saved.getIdAgence();

        // 3. findAll()
        System.out.println("Liste des agences :");

        agenceService.findAll()
                .forEach(System.out::println);

        assertFalse(agenceService.findAll().isEmpty());

        // 4. findById()
        Agence found = agenceService.findById(id);

        System.out.println("Agence trouvée : " + found);

        assertNotNull(found);
        assertEquals("Agence Ariana", found.getNom());

        // 5. Nombre d'agences
        int nombreAgences = agenceService.findAll().size();

        System.out.println("Nombre d'agences : " + nombreAgences);

        assertTrue(nombreAgences >= 1);

        // 6. Modification
        Agence modification = new Agence();
        modification.setNom("Agence Ariana Centre");
        modification.setVille("Ariana");
        modification.setAdresse("Centre Ariana");
        modification.setTelephone("70111222");

        Agence updated = agenceService.update(id, modification);

        System.out.println("Agence modifiée : " + updated);

        assertEquals("Agence Ariana Centre", updated.getNom());
        // 7. Suppression
        agenceService.deleteById(id);

        // 8. Vérification
        assertThrows(
                ResourceNotFoundException.class,
                () -> agenceService.findById(id)
        );

        System.out.println("Agence supprimée avec succès");


    }

    @Test
    void testClientService() {

        // 1. Premier client
        Client client1 = new Client();
        client1.setNom("Ben Salah");
        client1.setPrenom("Ghaith");
        client1.setEmail("ghaith@gmail.com");
        client1.setTelephone("22111222");
        client1.setNumPermis("PERMIS001");
        client1.setDateInscription(LocalDate.now());

        // 2. Deuxième client
        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Ahmed");
        client2.setEmail("ahmed@gmail.com");
        client2.setTelephone("99111222");
        client2.setNumPermis("PERMIS002");
        client2.setDateInscription(LocalDate.now());

        // 3. Enregistrement
        Client saved1 = clientService.create(client1);
        Client saved2 = clientService.create(client2);

        System.out.println("Client 1 enregistré : " + saved1);
        System.out.println("Client 2 enregistré : " + saved2);

        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());

        Long id1 = saved1.getIdClient();
        Long id2 = saved2.getIdClient();

        // 4. findAll()
        System.out.println("Liste des clients :");

        clientService.findAll()
                .forEach(System.out::println);

        assertFalse(clientService.findAll().isEmpty());

        // 5. findById()
        Client found = clientService.findById(id1);

        System.out.println("Client trouvé : " + found);

        assertNotNull(found);
        assertEquals("Ben Salah", found.getNom());

        // 6. Nombre de clients
        int nombreClients = clientService.findAll().size();

        System.out.println("Nombre de clients : " + nombreClients);

        assertTrue(nombreClients >= 2);

        // 7. Modification
        Client modification = new Client();
        modification.setNom("Ben Salah");
        modification.setPrenom("Ghaith");
        modification.setEmail("ghaith.new@gmail.com");
        modification.setTelephone("22111222");
        modification.setNumPermis("PERMIS001");
        modification.setDateInscription(saved1.getDateInscription());

        Client updated = clientService.update(id1, modification);

        System.out.println("Client modifié : " + updated);

        assertEquals(
                "ghaith.new@gmail.com",
                updated.getEmail()
        );
        // 8. Suppression
        clientService.deleteById(id1);
        clientService.deleteById(id2);

        // 9. Vérification
        assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.findById(id1)
        );

        assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.findById(id2)
        );

        System.out.println("Clients supprimés avec succès");
    }
    @Test
    void testVehiculeService() {

        // 1. Création
        Vehicule vehicule = new Vehicule();

        vehicule.setImmatriculation("123-TUN-456");
        vehicule.setMarque("Peugeot");
        vehicule.setModele("208");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("120.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        // 2. Enregistrement
        Vehicule saved = vehiculeService.create(vehicule);

        System.out.println("Véhicule enregistré : " + saved);

        assertNotNull(saved);
        assertNotNull(saved.getIdVehicule());

        Long id = saved.getIdVehicule();

        // 3. findAll()
        System.out.println("Liste des véhicules :");

        vehiculeService.findAll()
                .forEach(System.out::println);

        assertFalse(vehiculeService.findAll().isEmpty());

        // 4. findById()
        Vehicule found = vehiculeService.findById(id);

        System.out.println("Véhicule trouvé : " + found);

        assertNotNull(found);
        assertEquals("Peugeot", found.getMarque());
        assertEquals("208", found.getModele());

        // 5. Nombre de véhicules
        int nombreVehicules = vehiculeService.findAll().size();

        System.out.println(
                "Nombre de véhicules : " + nombreVehicules
        );

        assertTrue(nombreVehicules >= 1);

        // 6. Modification
        Vehicule modification = new Vehicule();

        modification.setImmatriculation("123-TUN-456");
        modification.setMarque("Peugeot");
        modification.setModele("308");
        modification.setCategorie(CategorieVehicule.BERLINE);
        modification.setTarifJournalier(
                new BigDecimal("150.00")
        );
        modification.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule updated =
                vehiculeService.update(id, modification);

        System.out.println(
                "Véhicule modifié : " + updated
        );

        assertEquals("308", updated.getModele());

        assertEquals(
                new BigDecimal("150.00"),
                updated.getTarifJournalier()
        );
        // 7. Suppression
        vehiculeService.deleteById(id);

        // 8. Vérification
        assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculeService.findById(id)
        );

        System.out.println(
                "Véhicule supprimé avec succès"
        );
    }
    }


