package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JedanMapper {


    public JedanDTO toDTO(Jedan model) {
        JedanDTO dto = new JedanDTO();
        dto.setStreet(model.getStreet());
        dto.setId(model.getId());

        return dto;
    }

     public List<JedanDTO> toDTO(List<Jedan> models) {
        List<JedanDTO> dtos = new ArrayList<JedanDTO>();
         for(Jedan model : models){
          JedanDTO dto = new JedanDTO();

        dto.setStreet(model.getStreet());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Jedan toModel(JedanDTO dto) {
        Jedan model = new Jedan();
        model.setStreet(dto.getStreet());
        return model;
    }
}