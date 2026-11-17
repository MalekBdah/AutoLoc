package tn.esprit.tpautoloc.service;

import java.util.List;

import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;

public interface IReservationService {

    ReservationResponseDTO findById(Long id);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO update(
            Long id,
            ReservationRequestDTO dto
    );

    void deleteById(Long id);

    ReservationResponseDTO annulerReservation(Long id);
}