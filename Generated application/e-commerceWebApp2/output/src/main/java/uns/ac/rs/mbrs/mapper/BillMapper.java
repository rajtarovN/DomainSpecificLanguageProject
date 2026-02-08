package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillMapper {


    public BillDTO toDTO(Bill model) {
        BillDTO dto = new BillDTO();
        dto.setCashier(model.getCashier());
        dto.setId(model.getId());

        return dto;
    }

     public List<BillDTO> toDTO(List<Bill> models) {
        List<BillDTO> dtos = new ArrayList<BillDTO>();
         for(Bill model : models){
          BillDTO dto = new BillDTO();

        dto.setCashier(model.getCashier());
        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Bill toModel(BillDTO dto) {
        Bill model = new Bill();
        model.setCashier(dto.getCashier());
        return model;
    }
}