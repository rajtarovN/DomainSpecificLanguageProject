package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

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
}