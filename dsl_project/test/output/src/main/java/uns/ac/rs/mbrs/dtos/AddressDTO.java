package uns.ac.rs.mbrs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private long id;

            private String street;
            private String city;
            private String zipCode;

}