package tn.esprit.tpautoloc.service.observer;

import tn.esprit.tpautoloc.domain.Reservation;

public interface IReservationObserver {

    void onReservationConfirmee(Reservation reservation);

    void onReservationAnnulee(Reservation reservation);
}