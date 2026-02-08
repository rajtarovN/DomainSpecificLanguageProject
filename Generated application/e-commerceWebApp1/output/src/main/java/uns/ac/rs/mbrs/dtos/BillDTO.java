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
public class BillDTO {

    private long id;

            private String cashier;
            private AddressDTO address;

            private CustomerDTO customer;



    private double totalPrice;

    private List<ItemWithPriceDTO>  itemWithPrice;


}