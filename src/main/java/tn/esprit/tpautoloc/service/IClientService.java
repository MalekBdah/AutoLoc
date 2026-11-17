package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.web.dto.ClientDTO;

public interface IClientService {

    ClientDTO create(ClientDTO dto);

    ClientDTO findById(Long id);

    List<ClientDTO> findAll();

    ClientDTO update(Long id, ClientDTO dto);

    void deleteById(Long id);
}