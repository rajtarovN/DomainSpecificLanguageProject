package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressMapper {


    public AddressDTO toDTO(Address model) {
        AddressDTO dto = new AddressDTO();
        dto.setStreet(model.getStreet());
        dto.setNumber(model.getNumber());
        dto.setZip(model.getZip());
        dto.setId(model.getId());

        return dto;
    }

    public List<AddressDTO> toDTO(List<Address> models) {
        List<AddressDTO> dtos = new ArrayList<AddressDTO>();
         for(Address model : models){
            AddressDTO dto = new AddressDTO();

            dto.setStreet(model.getStreet());
            dto.setNumber(model.getNumber());
            dto.setZip(model.getZip());
            dto.setId(model.getId());
            dtos.add(dto);
        }
        return dtos;
    }

    public Address toModel(AddressDTO dto) {
        Address model = new Address();
        model.setStreet(dto.getStreet());
        model.setNumber(dto.getNumber());
        model.setZip(dto.getZip());
        return model;
    }
}