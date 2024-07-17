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
public class ItemDTO {

    private long id;

            private String name;
            private int quantity;



            private List<ItemWithPriceDTO>  itemWithPrice;
                private List<BasketDTO>  basket;


}