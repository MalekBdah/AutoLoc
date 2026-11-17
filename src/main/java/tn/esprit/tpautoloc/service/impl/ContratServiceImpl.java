package tn.esprit.tpautoloc.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IContratRepository;
import tn.esprit.tpautoloc.service.IContratService;
import tn.esprit.tpautoloc.web.dto.ContratDTO;
import tn.esprit.tpautoloc.web.mapper.ContratMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratDTO create(ContratDTO dto) {

        if (dto.getId() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant"
            );
        }

        if (dto.getMontantTotal() != null
                && dto.getMontantTotal().signum() < 0) {

            throw new IllegalArgumentException(
                    "Le montant total ne peut pas être négatif"
            );
        }

        Contrat contrat = contratMapper.toEntity(dto);

        Contrat saved = contratRepository.save(contrat);

        return contratMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ContratDTO findById(Long id) {

        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));

        return contratMapper.toDto(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> findAll() {

        return contratRepository.findAll()
                .stream()
                .map(contratMapper::toDto)
                .toList();
    }

    @Override
    public ContratDTO update(Long id, ContratDTO dto) {

        Contrat existant = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));

        if (dto.getMontantTotal() != null
                && dto.getMontantTotal().signum() < 0) {

            throw new IllegalArgumentException(
                    "Le montant total ne peut pas être négatif"
            );
        }

        existant.setDateSignature(dto.getDateSignature());
        existant.setMontantTotal(dto.getMontantTotal());
        existant.setValide(dto.isValide());

        Contrat saved = contratRepository.save(existant);

        return contratMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {

        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }

        contratRepository.deleteById(id);
    }
}