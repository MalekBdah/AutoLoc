package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

public interface IVehiculeService {

    VehiculeDTO create(VehiculeDTO dto);

    VehiculeDTO findById(Long id);

    List<VehiculeDTO> findAll();

    VehiculeDTO update(Long id, VehiculeDTO dto);

    void deleteById(Long id);

    void affecterVehiculeAAgence(Long vehiculeId, Long agenceId);

    void desaffecterVehiculeDeAgence(Long vehiculeId);
}