package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminMapper {


    public AdminDTO toDTO(Admin model) {
        AdminDTO dto = new AdminDTO();
        dto.setId(model.getId());

        dto.setUsername(model.getUsername());
        dto.setPassword(model.getPassword());
        dto.setDeleted(model.isDeleted());
        dto.setLoggedFirstTime(model.isLoggedFirstTime());
        dto.setRole(model.getRole().getName());
        return dto;
    }

     public List<AdminDTO> toDTO(List<Admin> models) {
        List<AdminDTO> dtos = new ArrayList<AdminDTO>();
         for(Admin model : models){
          AdminDTO dto = new AdminDTO();

        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Admin toModel(AdminDTO dto) {
        Admin model = new Admin();
        model.setUsername(dto.getUsername());
        model.setPassword(dto.getPassword());
        model.setDeleted(dto.isDeleted());
        model.setLoggedFirstTime(dto.isLoggedFirstTime());

        return model;
    }
}