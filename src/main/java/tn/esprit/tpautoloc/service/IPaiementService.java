package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;

public interface IPaiementService {

    PaiementDTO findById(Long id);

    List<PaiementDTO> findAll();
}