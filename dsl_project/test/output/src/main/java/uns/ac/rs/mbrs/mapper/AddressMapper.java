package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressMapper {


    public AddressDTO toDTO(Address model) {
        AddressDTO dto = new AddressDTO();
        dto.setStreet(model.getStreet());
        dto.setCity(model.getCity());
        dto.setZipCode(model.getZipCode());
        dto.setId(model.getId());
        return dto;
    }
}