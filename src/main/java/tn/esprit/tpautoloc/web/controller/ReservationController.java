package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {

        return ResponseEntity.ok(
                reservationService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ReservationRequestDTO dto) {

        return ResponseEntity.ok(
                reservationService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<ReservationResponseDTO> annuler(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.annulerReservation(id)
        );
    }
}