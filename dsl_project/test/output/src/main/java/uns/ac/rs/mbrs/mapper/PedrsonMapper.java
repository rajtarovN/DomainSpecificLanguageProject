package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedrsonMapper {


    public PedrsonDTO toDTO(Pedrson model) {
        PedrsonDTO dto = new PedrsonDTO();
        dto.setAge(model.getAge());
        dto.setId(model.getId());

        return dto;
    }

     public List<PedrsonDTO> toDTO(List<Pedrson> models) {
        List<PedrsonDTO> dtos = new ArrayList<PedrsonDTO>();
         for(Pedrson model : models){
          PedrsonDTO dto = new PedrsonDTO();

        dto.setAge(model.getAge());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Pedrson toModel(PedrsonDTO dto) {
        Pedrson model = new Pedrson();
        model.setAge(dto.getAge());
        return model;
    }
}