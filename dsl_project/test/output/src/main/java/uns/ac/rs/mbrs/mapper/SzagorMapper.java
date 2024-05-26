package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SzagorMapper {


    public SzagorDTO toDTO(Szagor model) {
        SzagorDTO dto = new SzagorDTO();
        dto.setName(model.getName());
        dto.setId(model.getId());
        return dto;
    }

    public Szagor toModel(SzagorDTO dto) {
        Szagor model = new Szagor();
        model.setName(dto.getName());
        return model;
    }
}