package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketMapper {

    private final ItemMapper itemMapper;

    public BasketMapper(
            ItemMapper itemMapper
    ) {
        this.itemMapper = itemMapper;
    }

    public BasketDTO toDTO(Basket model) {
        BasketDTO dto = new BasketDTO();
        if (model.getQuantity() != null) {
            List<Integer> quantities = new ArrayList<>(model.getQuantity());
            dto.setQuantity(quantities);
        }
        if (model.getItem() != null) {
            dto.setItem(itemMapper.toDTO(model.getItem()));
        }
        dto.setId(model.getId());

        return dto;
    }

    public List<BasketDTO> toDTO(List<Basket> models) {
        List<BasketDTO> dtos = new ArrayList<BasketDTO>();
        for (Basket model : models) {
            BasketDTO dto = new BasketDTO();

            if (model.getQuantity() != null) {
                List<Integer> quantities = new ArrayList<>(model.getQuantity());
                dto.setQuantity(quantities);
            }
            if (model.getItem() != null) {
                dto.setItem(itemMapper.toDTO(model.getItem()));
            }
            dto.setId(model.getId());
            dtos.add(dto);
        }
        return dtos;
    }

    public Basket toModel(BasketDTO dto) {
        Basket model = new Basket();
        return model;
    }
}