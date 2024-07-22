package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
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

     public List<PersonDTO> toDTO(List<Person> models) {
        List<PersonDTO> dtos = new ArrayList<PersonDTO>();
         for(Person model : models){
          PersonDTO dto = new PersonDTO();

        dto.setAge(model.getAge());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Person toModel(PersonDTO dto) {
        Person model = new Person();
        model.setAge(dto.getAge());
        return model;
    }
}