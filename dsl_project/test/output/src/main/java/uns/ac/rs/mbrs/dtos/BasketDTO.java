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
public class BasketDTO {

    private long id;

            private String formular;
            private CustomerDTO customer;






                   private List<Integer> quantity;
                    private List<ItemDTO>  item;







}