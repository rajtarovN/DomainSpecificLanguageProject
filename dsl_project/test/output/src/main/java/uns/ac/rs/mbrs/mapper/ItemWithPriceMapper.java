package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemWithPriceMapper {


    public ItemWithPriceDTO toDTO(ItemWithPrice model) {
        ItemWithPriceDTO dto = new ItemWithPriceDTO();
        dto.setCurrentPrice(model.getCurrentPrice());
        dto.setCurrent(model.isIscurrent());
        dto.setId(model.getId());
        return dto;
    }

    public ItemWithPrice toModel(ItemWithPriceDTO dto) {
        ItemWithPrice model = new ItemWithPrice();
        model.setId(dto.getId());
        model.setCurrentPrice(dto.getCurrentPrice());
        model.setIscurrent(dto.isCurrent());
        model.setDeleted(dto.isDeleted());
        return model;
    }
}