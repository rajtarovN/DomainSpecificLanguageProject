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
public class ItemWithPriceDTO {

    private long id;
    private boolean deleted;




    private double currentPrice;

    private boolean current; //is it still valid



                private ItemDTO  item;


}