package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SellerMapper {


    public SellerDTO toDTO(Seller model) {
        SellerDTO dto = new SellerDTO();
        dto.setId(model.getId());

        dto.setUsername(model.getUsername());
        dto.setPassword(model.getPassword());
        dto.setDeleted(model.isDeleted());
        dto.setLoggedFirstTime(model.isLoggedFirstTime());
        dto.setRole(model.getRole().getName());
        return dto;
    }

     public List<SellerDTO> toDTO(List<Seller> models) {
        List<SellerDTO> dtos = new ArrayList<SellerDTO>();
         for(Seller model : models){
          SellerDTO dto = new SellerDTO();

        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Seller toModel(SellerDTO dto) {
        Seller model = new Seller();
        model.setUsername(dto.getUsername());
        model.setPassword(dto.getPassword());
        model.setDeleted(dto.isDeleted());
        model.setLoggedFirstTime(dto.isLoggedFirstTime());

        return model;
    }
}