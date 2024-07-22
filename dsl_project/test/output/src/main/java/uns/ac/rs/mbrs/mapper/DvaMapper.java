package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DvaMapper {


    public DvaDTO toDTO(Dva model) {
        DvaDTO dto = new DvaDTO();
        dto.setSds(model.getSds());
        dto.setId(model.getId());

        return dto;
    }

     public List<DvaDTO> toDTO(List<Dva> models) {
        List<DvaDTO> dtos = new ArrayList<DvaDTO>();
         for(Dva model : models){
          DvaDTO dto = new DvaDTO();

        dto.setSds(model.getSds());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Dva toModel(DvaDTO dto) {
        Dva model = new Dva();
        model.setSds(dto.getSds());
        return model;
    }
}