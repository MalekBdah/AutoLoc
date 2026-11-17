package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;

import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;

import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Test
    void testLectureReservation() {

        Client client = clientRepository.findAll().get(0);
        Vehicule vehicule = vehiculeRepository.findAll().get(0);

        Reservation reservation = new Reservation();

        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation saved =
                reservationRepository.save(reservation);

        assertNotNull(saved);
        assertNotNull(saved.getIdReservation());

        ReservationResponseDTO found =
                reservationService.findById(
                        saved.getIdReservation()
                );

        assertNotNull(found);
        assertNotNull(found.getId());

        assertEquals(
                StatutReservation.CONFIRMEE,
                found.getStatut()
        );

        assertEquals(
                client.getIdClient(),
                found.getClientId()
        );

        assertEquals(
                vehicule.getIdVehicule(),
                found.getVehiculeId()
        );

        reservationRepository.deleteById(
                saved.getIdReservation()
        );
    }

    @Test
    void testAnnulationReservation() {

        Client client = clientRepository.findAll().get(0);
        Vehicule vehicule = vehiculeRepository.findAll().get(0);

        Reservation reservation = new Reservation();

        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation saved =
                reservationRepository.save(reservation);

        ReservationResponseDTO annulee =
                reservationService.annulerReservation(
                        saved.getIdReservation()
                );

        assertNotNull(annulee);

        assertEquals(
                StatutReservation.ANNULEE,
                annulee.getStatut()
        );

        reservationRepository.deleteById(
                saved.getIdReservation()
        );
    }
}