package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TriMapper {


    public TriDTO toDTO(Tri model) {
        TriDTO dto = new TriDTO();
        dto.setSdssdfe(model.getSdssdfe());
        dto.setId(model.getId());
        return dto;
    }
}