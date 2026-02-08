package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {


    public ItemDTO toDTO(Item model) {
        ItemDTO dto = new ItemDTO();
        dto.setName(model.getName());
        dto.setQuantity(model.getQuantity());
        dto.setId(model.getId());

        return dto;
    }

     public List<ItemDTO> toDTO(List<Item> models) {
        List<ItemDTO> dtos = new ArrayList<ItemDTO>();
         for(Item model : models){
          ItemDTO dto = new ItemDTO();

        dto.setName(model.getName());
        dto.setQuantity(model.getQuantity());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Item toModel(ItemDTO dto) {
        Item model = new Item();
        model.setName(dto.getName());
        model.setQuantity(dto.getQuantity());
        return model;
    }
}