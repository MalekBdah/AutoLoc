package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;

import tn.esprit.tpautoloc.exception.ResourceNotFoundException;

import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IClientRepository;

import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.service.IVehiculeService;

import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import tn.esprit.tpautoloc.web.dto.ClientDTO;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    @Test
    void contextLoads() {

        assertNotNull(agenceRepository);
        assertNotNull(clientRepository);
        assertNotNull(agenceService);
        assertNotNull(clientService);
        assertNotNull(vehiculeService);
    }

    @Test
    void testAgenceService() {

        // 1. Création
        AgenceDTO agence = new AgenceDTO();

        agence.setNom("Agence Ariana");
        agence.setVille("Ariana");
        agence.setAdresse("Centre Ariana");
        agence.setTelephone("70111222");

        // 2. Enregistrement
        AgenceDTO saved = agenceService.create(agence);

        System.out.println("Agence enregistrée : " + saved);

        assertNotNull(saved);
        assertNotNull(saved.getId());

        Long id = saved.getId();

        // 3. findAll()
        System.out.println("Liste des agences :");

        agenceService.findAll()
                .forEach(System.out::println);

        assertFalse(agenceService.findAll().isEmpty());

        // 4. findById()
        AgenceDTO found = agenceService.findById(id);

        System.out.println("Agence trouvée : " + found);

        assertNotNull(found);
        assertEquals("Agence Ariana", found.getNom());

        // 5. Nombre d'agences
        int nombreAgences = agenceService.findAll().size();

        System.out.println(
                "Nombre d'agences : " + nombreAgences
        );

        assertTrue(nombreAgences >= 1);

        // 6. Modification
        AgenceDTO modification = new AgenceDTO();

        modification.setNom("Agence Ariana Centre");
        modification.setVille("Ariana");
        modification.setAdresse("Centre Ariana");
        modification.setTelephone("70111222");

        AgenceDTO updated =
                agenceService.update(id, modification);

        System.out.println(
                "Agence modifiée : " + updated
        );

        assertEquals(
                "Agence Ariana Centre",
                updated.getNom()
        );

        // 7. Suppression
        agenceService.deleteById(id);

        // 8. Vérification
        assertThrows(
                ResourceNotFoundException.class,
                () -> agenceService.findById(id)
        );

        System.out.println(
                "Agence supprimée avec succès"
        );
    }

    @Test
    void testClientService() {

        // 1. Premier client
        ClientDTO client1 = new ClientDTO();

        client1.setNom("Ben Salah");
        client1.setPrenom("Ghaith");
        client1.setEmail("ghaith@gmail.com");
        client1.setTelephone("22111222");
        client1.setNumPermis("PERMIS001");
        client1.setDateInscription(LocalDate.now());

        // 2. Deuxième client
        ClientDTO client2 = new ClientDTO();

        client2.setNom("Trabelsi");
        client2.setPrenom("Ahmed");
        client2.setEmail("ahmed@gmail.com");
        client2.setTelephone("99111222");
        client2.setNumPermis("PERMIS002");
        client2.setDateInscription(LocalDate.now());

        // 3. Enregistrement
        ClientDTO saved1 =
                clientService.create(client1);

        ClientDTO saved2 =
                clientService.create(client2);

        System.out.println(
                "Client 1 enregistré : " + saved1
        );

        System.out.println(
                "Client 2 enregistré : " + saved2
        );

        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());

        Long id1 = saved1.getId();
        Long id2 = saved2.getId();

        // 4. findAll()
        System.out.println("Liste des clients :");

        clientService.findAll()
                .forEach(System.out::println);

        assertFalse(
                clientService.findAll().isEmpty()
        );

        // 5. findById()
        ClientDTO found =
                clientService.findById(id1);

        System.out.println(
                "Client trouvé : " + found
        );

        assertNotNull(found);

        assertEquals(
                "Ben Salah",
                found.getNom()
        );

        // 6. Nombre de clients
        int nombreClients =
                clientService.findAll().size();

        System.out.println(
                "Nombre de clients : " + nombreClients
        );

        assertTrue(nombreClients >= 2);

        // 7. Modification
        ClientDTO modification =
                new ClientDTO();

        modification.setNom("Ben Salah");
        modification.setPrenom("Ghaith");
        modification.setEmail(
                "ghaith.new@gmail.com"
        );
        modification.setTelephone("22111222");
        modification.setNumPermis("PERMIS001");
        modification.setDateInscription(
                saved1.getDateInscription()
        );

        ClientDTO updated =
                clientService.update(
                        id1,
                        modification
                );

        System.out.println(
                "Client modifié : " + updated
        );

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

        System.out.println(
                "Clients supprimés avec succès"
        );
    }

    @Test
    void testVehiculeService() {

        /*
         * Le VehiculeDTO utilise maintenant agenceId.
         * On crée donc d'abord une agence.
         */

        AgenceDTO agence = new AgenceDTO();

        agence.setNom("Agence Test Vehicule");
        agence.setVille("Tunis");
        agence.setAdresse("Centre Tunis");
        agence.setTelephone("71111222");

        AgenceDTO agenceSaved =
                agenceService.create(agence);

        assertNotNull(agenceSaved.getId());

        Long agenceId = agenceSaved.getId();

        // 1. Création véhicule
        VehiculeDTO vehicule =
                new VehiculeDTO();

        vehicule.setImmatriculation(
                "123-TUN-456"
        );

        vehicule.setMarque("Peugeot");
        vehicule.setModele("208");

        vehicule.setCategorie(
                CategorieVehicule.CITADINE
        );

        vehicule.setTarifJournalier(
                new BigDecimal("120.00")
        );

        vehicule.setStatut(
                StatutVehicule.DISPONIBLE
        );

        vehicule.setAgenceId(agenceId);

        // 2. Enregistrement
        VehiculeDTO saved =
                vehiculeService.create(vehicule);

        System.out.println(
                "Véhicule enregistré : " + saved
        );

        assertNotNull(saved);
        assertNotNull(saved.getId());

        assertEquals(
                agenceId,
                saved.getAgenceId()
        );

        Long id = saved.getId();

        // 3. findAll()
        System.out.println(
                "Liste des véhicules :"
        );

        vehiculeService.findAll()
                .forEach(System.out::println);

        assertFalse(
                vehiculeService.findAll().isEmpty()
        );

        // 4. findById()
        VehiculeDTO found =
                vehiculeService.findById(id);

        System.out.println(
                "Véhicule trouvé : " + found
        );

        assertNotNull(found);

        assertEquals(
                "Peugeot",
                found.getMarque()
        );

        assertEquals(
                "208",
                found.getModele()
        );

        // 5. Nombre de véhicules
        int nombreVehicules =
                vehiculeService.findAll().size();

        System.out.println(
                "Nombre de véhicules : "
                        + nombreVehicules
        );

        assertTrue(nombreVehicules >= 1);

        // 6. Modification
        VehiculeDTO modification =
                new VehiculeDTO();

        modification.setImmatriculation(
                "123-TUN-456"
        );

        modification.setMarque("Peugeot");
        modification.setModele("308");

        modification.setCategorie(
                CategorieVehicule.BERLINE
        );

        modification.setTarifJournalier(
                new BigDecimal("150.00")
        );

        modification.setStatut(
                StatutVehicule.DISPONIBLE
        );

        modification.setAgenceId(agenceId);

        VehiculeDTO updated =
                vehiculeService.update(
                        id,
                        modification
                );

        System.out.println(
                "Véhicule modifié : " + updated
        );

        assertEquals(
                "308",
                updated.getModele()
        );

        assertEquals(
                new BigDecimal("150.00"),
                updated.getTarifJournalier()
        );

        // 7. Suppression véhicule
        vehiculeService.deleteById(id);

        // 8. Vérification véhicule
        assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculeService.findById(id)
        );

        // 9. Nettoyage agence de test
        agenceService.deleteById(agenceId);

        System.out.println(
                "Véhicule supprimé avec succès"
        );
    }
}