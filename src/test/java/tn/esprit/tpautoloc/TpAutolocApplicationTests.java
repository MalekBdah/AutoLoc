package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.repository.IClientRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testAgenceRepository() {

        // 1. Création
        Agence agence = new Agence();
        agence.setNom("Agence Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("Centre Ville");
        agence.setTelephone("12345678");

        // 2. Enregistrement
        Agence saved = agenceRepository.saveAndFlush(agence);

        System.out.println("Agence enregistrée : " + saved);

        assertNotNull(saved);
        assertNotNull(saved.getIdAgence());

        Long id = saved.getIdAgence();

        // 3. findAll()
        System.out.println("Liste des agences :");
        agenceRepository.findAll()
                .forEach(System.out::println);

        // 4. findById()
        Agence found = agenceRepository
                .findById(id)
                .orElse(null);

        System.out.println("Agence trouvée : " + found);

        assertNotNull(found);
        assertEquals("Agence Tunis", found.getNom());

        // 5. existsById()
        boolean exists = agenceRepository.existsById(id);

        System.out.println("L'agence existe : " + exists);

        assertTrue(exists);

        // 6. count()
        long nombreAgences = agenceRepository.count();

        System.out.println("Nombre d'agences : " + nombreAgences);

        // 7. deleteById()
        agenceRepository.deleteById(id);
        agenceRepository.flush();

        // 8. Vérification après suppression
        boolean existsAfterDelete =
                agenceRepository.existsById(id);

        System.out.println(
                "L'agence existe après suppression : "
                        + existsAfterDelete
        );

        assertFalse(existsAfterDelete);
    }
    @Autowired
    private IClientRepository clientRepository;
    @Test
    void testClientRepository() {

        // 1. Créer deux clients
        Client client1 = new Client();
        client1.setNom("Ben Salah");
        client1.setPrenom("Ghaith");
        client1.setEmail("ghaith@gmail.com");
        client1.setTelephone("22111222");
        client1.setNumPermis("PERMIS001");
        client1.setDateInscription(java.time.LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Ahmed");
        client2.setEmail("ahmed@gmail.com");
        client2.setTelephone("99111222");
        client2.setNumPermis("PERMIS002");
        client2.setDateInscription(java.time.LocalDate.now());

        // 2. Enregistrer les deux clients
        Client saved1 = clientRepository.save(client1);
        Client saved2 = clientRepository.save(client2);

        System.out.println("Client 1 enregistré : " + saved1);
        System.out.println("Client 2 enregistré : " + saved2);

        // 3. findAll()
        System.out.println("Liste des clients :");
        clientRepository.findAll()
                .forEach(System.out::println);

        // 4. findById()
        Client found = clientRepository
                .findById(saved1.getIdClient())
                .orElse(null);

        System.out.println("Client trouvé : " + found);

        // 5. existsById() avec ID existant
        boolean existe = clientRepository.existsById(saved1.getIdClient());
        System.out.println("Client existant ? " + existe);

        // 6. existsById() avec ID inexistant
        boolean inexistant = clientRepository.existsById(999999L);
        System.out.println("Client 999999 existe ? " + inexistant);

        // 7. count()
        long nombreClients = clientRepository.count();
        System.out.println("Nombre de clients : " + nombreClients);
    }

}