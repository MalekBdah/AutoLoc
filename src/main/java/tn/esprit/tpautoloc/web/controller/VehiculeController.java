package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @GetMapping
    public ResponseEntity<List<VehiculeDTO>> findAll() {

        return ResponseEntity.ok(
                vehiculeService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vehiculeService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<VehiculeDTO> create(
            @Valid @RequestBody VehiculeDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        vehiculeService.create(dto)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody VehiculeDTO dto) {

        return ResponseEntity.ok(
                vehiculeService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        vehiculeService.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{vehiculeId}/agence/{agenceId}")
    public ResponseEntity<Void> affecterVehiculeAAgence(
            @PathVariable Long vehiculeId,
            @PathVariable Long agenceId) {

        vehiculeService.affecterVehiculeAAgence(
                vehiculeId,
                agenceId
        );

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{vehiculeId}/desaffecter-agence")
    public ResponseEntity<Void> desaffecterVehiculeDeAgence(
            @PathVariable Long vehiculeId) {

        vehiculeService.desaffecterVehiculeDeAgence(
                vehiculeId
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}