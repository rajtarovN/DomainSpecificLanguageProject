package uns.ac.rs.mbrs.mapper;

import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {


    public CustomerDTO toDTO(Customer model) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(model.getId());

        dto.setUsername(model.getUsername());
        dto.setPassword(model.getPassword());
        dto.setDeleted(model.isDeleted());
        dto.setLoggedFirstTime(model.isLoggedFirstTime());
        dto.setRole(model.getRole().getName());
        return dto;
    }

     public List<CustomerDTO> toDTO(List<Customer> models) {
        List<CustomerDTO> dtos = new ArrayList<CustomerDTO>();
         for(Customer model : models){
          CustomerDTO dto = new CustomerDTO();

        dto.setId(model.getId());
        dtos.add(dto);
        }
        return dtos;
    }

    public Customer toModel(CustomerDTO dto) {
        Customer model = new Customer();
        model.setUsername(dto.getUsername());
        model.setPassword(dto.getPassword());
        model.setDeleted(dto.isDeleted());
        model.setLoggedFirstTime(dto.isLoggedFirstTime());

        return model;
    }
}