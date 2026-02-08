package uns.ac.rs.mbrs.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private long id;

    private String street;
    private String number;
    private String zip;
    private BillDTO bill;




}