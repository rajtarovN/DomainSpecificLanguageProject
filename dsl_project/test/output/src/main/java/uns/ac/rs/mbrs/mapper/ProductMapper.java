package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {


    public ProductDTO toDTO(Product model) {
        ProductDTO dto = new ProductDTO();
        dto.setName(model.getName());
        dto.setPrice(model.getPrice());
        dto.setId(model.getId());
        return dto;
    }
}