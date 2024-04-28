package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {


    public PersonDTO toDTO(Person model) {
        PersonDTO dto = new PersonDTO();
        dto.setAge(model.getAge());
        dto.setId(model.getId());
        return dto;
    }
}