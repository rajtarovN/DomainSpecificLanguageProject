package uns.ac.rs.mbrs.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import uns.ac.rs.mbrs.repository.BasketRepository;
import uns.ac.rs.mbrs.repository.ItemRepository;
import uns.ac.rs.mbrs.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketMapper {
    private final ItemMapper itemMapper;

    public BasketMapper(
            ItemMapper itemMapper
    ) {
        this.itemMapper = itemMapper;}


    public BasketDTO toDTO(Basket model) {
        BasketDTO dto = new BasketDTO();
        dto.setFormular(model.getFormular());
        dto.setId(model.getId());
        if (model.getQuantity()!=null){
            dto.setQuantity(model.getQuantity());

        }
        if (model.getItem()!=null){
            dto.setItem(itemMapper.toDTO(model.getItem()));
        }
        return dto;
    }

    public Basket toModel(BasketDTO dto) {
        Basket model = new Basket();
        model.setFormular(dto.getFormular());
        return model;
    }
}