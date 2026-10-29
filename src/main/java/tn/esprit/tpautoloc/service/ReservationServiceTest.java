package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private IReservationService reservationService;

    @Test
    void testCreationReservation() {

        Client client = new Client();
        client.setNom("Ben Salah");
        client.setPrenom("Ahmed");
        client.setEmail("ahmed@gmail.com");
        client.setTelephone("22111222");

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation saved = reservationService.create(reservation);

        assertNotNull(saved);
        assertNotNull(saved.getIdReservation());
        assertEquals(StatutReservation.CONFIRMEE, saved.getStatut());
    }

    @Test
    void testAnnulationReservation() {

        Reservation reservation = reservationService.findAll().get(0);

        Reservation annulee =
                reservationService.annulerReservation(
                        reservation.getIdReservation()
                );

        assertEquals(
                StatutReservation.ANNULEE,
                annulee.getStatut()
        );
    }
}