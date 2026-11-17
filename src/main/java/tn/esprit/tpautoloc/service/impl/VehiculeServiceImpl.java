package tn.esprit.tpautoloc.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;
import tn.esprit.tpautoloc.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDTO create(VehiculeDTO dto) {

        if (dto.getId() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant"
            );
        }

        if (dto.getTarifJournalier() != null
                && dto.getTarifJournalier().signum() < 0) {

            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif"
            );
        }

        Vehicule vehicule = vehiculeMapper.toEntity(dto);

        if (dto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(dto.getAgenceId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Agence",
                                    dto.getAgenceId()
                            ));

            vehicule.setAgence(agence);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO findById(Long id) {

        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id));

        return vehiculeMapper.toDto(vehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {

        return vehiculeRepository.findAll()
                .stream()
                .map(vehiculeMapper::toDto)
                .toList();
    }

    @Override
    public VehiculeDTO update(Long id, VehiculeDTO dto) {

        Vehicule existant = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id));

        if (dto.getTarifJournalier() != null
                && dto.getTarifJournalier().signum() < 0) {

            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif"
            );
        }

        existant.setImmatriculation(dto.getImmatriculation());
        existant.setMarque(dto.getMarque());
        existant.setModele(dto.getModele());
        existant.setCategorie(dto.getCategorie());
        existant.setTarifJournalier(dto.getTarifJournalier());
        existant.setStatut(dto.getStatut());

        if (dto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(dto.getAgenceId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Agence",
                                    dto.getAgenceId()
                            ));

            existant.setAgence(agence);
        }

        Vehicule saved = vehiculeRepository.save(existant);

        return vehiculeMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {

        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }

        vehiculeRepository.deleteById(id);
    }

    @Override
    public void affecterVehiculeAAgence(Long vehiculeId, Long agenceId) {

        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", vehiculeId));

        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", agenceId));

        vehicule.setAgence(agence);

        vehiculeRepository.save(vehicule);
    }

    @Override
    public void desaffecterVehiculeDeAgence(Long vehiculeId) {

        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", vehiculeId));

        if (vehicule.getAgence() == null) {
            throw new IllegalArgumentException(
                    "Le véhicule n'est affecté à aucune agence"
            );
        }

        vehicule.setAgence(null);

        vehiculeRepository.save(vehicule);
    }
}