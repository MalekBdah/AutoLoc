package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.web.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", source = "idClient")
    ClientDTO toDto(Client client);

    @Mapping(target = "idClient", source = "id")
    Client toEntity(ClientDTO dto);
}