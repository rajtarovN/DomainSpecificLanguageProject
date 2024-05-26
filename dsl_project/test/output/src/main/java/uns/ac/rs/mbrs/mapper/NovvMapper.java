package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NovvMapper {


    public NovvDTO toDTO(Novv model) {
        NovvDTO dto = new NovvDTO();
        dto.setName(model.getName());
        dto.setId(model.getId());
        return dto;
    }

    public Novv toModel(NovvDTO dto) {
        Novv model = new Novv();
        model.setName(dto.getName());
        return model;
    }
}