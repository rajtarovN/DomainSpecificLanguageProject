package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillMapper {


    public BillDTO toDTO(Bill model) {
        BillDTO dto = new BillDTO();
        dto.setNeki_tekst(model.getNeki_tekst());
        dto.setId(model.getId());
        return dto;
    }

    public Bill toModel(BillDTO dto) {
        Bill model = new Bill();
        model.setNeki_tekst(dto.getNeki_tekst());
        return model;
    }
}