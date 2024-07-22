package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NovvMapper {


    public NovvDTO toDTO(Novv model) {
        NovvDTO dto = new NovvDTO();
        dto.setName(model.getName());
        dto.setId(model.getId());

        return dto;
    }

     public List<NovvDTO> toDTO(List<Novv> models) {
        List<NovvDTO> dtos = new ArrayList<NovvDTO>();
         for(Novv model : models){
          NovvDTO dto = new NovvDTO();

        dto.setName(model.getName());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Novv toModel(NovvDTO dto) {
        Novv model = new Novv();
        model.setName(dto.getName());
        return model;
    }
}