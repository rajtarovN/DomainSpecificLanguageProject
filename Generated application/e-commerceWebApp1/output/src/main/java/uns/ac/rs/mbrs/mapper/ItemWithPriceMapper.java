package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemWithPriceMapper {

 private final ItemMapper itemMapper;
     public ItemWithPriceMapper(
                ItemMapper itemMapper
        ) {
            this.itemMapper = itemMapper;}

    public ItemWithPriceDTO toDTO(ItemWithPrice model) {
        ItemWithPriceDTO dto = new ItemWithPriceDTO();
        dto.setId(model.getId());
        dto.setCurrentPrice(model.getCurrentPrice());
        dto.setCurrent(model.isIscurrent());
         dto.setItem(model.getItem()!=null?itemMapper.toDTO(model.getItem()):new ItemDTO());

        return dto;
    }

     public List<ItemWithPriceDTO> toDTO(List<ItemWithPrice> models) {
        List<ItemWithPriceDTO> dtos = new ArrayList<ItemWithPriceDTO>();
         for(ItemWithPrice model : models){
          ItemWithPriceDTO dto = new ItemWithPriceDTO();

        dto.setId(model.getId());
        dto.setCurrentPrice(model.getCurrentPrice());
        dto.setCurrent(model.isIscurrent());
         dto.setItem(model.getItem()!=null?itemMapper.toDTO(model.getItem()):new ItemDTO());
        dtos.add(dto);
        }
        return dtos;
    }

    public ItemWithPrice toModel(ItemWithPriceDTO dto) {
        ItemWithPrice model = new ItemWithPrice();
                  model.setCurrentPrice(dto.getCurrentPrice());
                  model.setDeleted(false);
                  model.setIscurrent(true);
                  model.setId(dto.getId());
                  model.setItem(itemMapper.toModel(dto.getItem()));
        return model;
    }
}