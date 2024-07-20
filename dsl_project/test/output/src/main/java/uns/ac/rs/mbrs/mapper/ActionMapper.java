package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActionMapper {


    public ActionDTO toDTO(Action model) {
        ActionDTO dto = new ActionDTO();
            dto.setOriginalCode(model.getOriginalCode());
            dto.setDateFrom(model.getDateFrom());
            dto.setDateTo(model.getDateTo());
        dto.setName(model.getName());
        dto.setId(model.getId());

        return dto;
    }

     public List<ActionDTO> toDTO(List<Action> models) {
        List<ActionDTO> dtos = new ArrayList<ActionDTO>();
         for(Action model : models){
          ActionDTO dto = new ActionDTO();

        dto.setName(model.getName());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Action toModel(ActionDTO dto) {
        Action model = new Action();
        model.setName(dto.getName());
            model.setOriginalCode(dto.getOriginalCode());
        model.setDateFrom(dto.getDateFrom());
        model.setDateTo(dto.getDateTo());
        return model;
    }
}