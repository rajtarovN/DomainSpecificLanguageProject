package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketMapper {


    public BasketDTO toDTO(Basket model) {
        BasketDTO dto = new BasketDTO();
        dto.setFormular(model.getFormular());
        dto.setId(model.getId());
        return dto;
    }

    public Basket toModel(BasketDTO dto) {
        Basket model = new Basket();
        model.setFormular(dto.getFormular());
        return model;
    }
}