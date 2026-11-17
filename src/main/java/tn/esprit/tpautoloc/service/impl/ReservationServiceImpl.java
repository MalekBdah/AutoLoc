package tn.esprit.tpautoloc.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.service.observer.ReservationSubject;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tpautoloc.web.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;

    private final ReservationMapper reservationMapper;
    private final ReservationSubject reservationSubject;

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO findById(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));

        return reservationMapper.toDto(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findAll() {

        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @Override
    public ReservationResponseDTO update(
            Long id,
            ReservationRequestDTO dto) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));

        verifierDates(dto);

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Client",
                                dto.getClientId()
                        ));

        Vehicule vehicule = vehiculeRepository.findById(dto.getVehiculeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicule",
                                dto.getVehiculeId()
                        ));

        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setClient(client);
        reservation.setVehicule(vehicule);

        Reservation saved =
                reservationRepository.save(reservation);

        return reservationMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {

        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Reservation",
                    id
            );
        }

        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationResponseDTO annulerReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reservation",
                                id
                        ));

        reservation.setStatut(
                StatutReservation.ANNULEE
        );

        Reservation saved =
                reservationRepository.save(reservation);

        reservationSubject.notifierAnnulation(saved);

        return reservationMapper.toDto(saved);
    }

    private void verifierDates(
            ReservationRequestDTO dto) {

        if (dto.getDateDebut() != null
                && dto.getDateFin() != null
                && dto.getDateFin()
                .isBefore(dto.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }
}