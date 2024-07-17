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
public class PersonDTO {

    private long id;

            private String name;
            private String lastName;
            private String username;
            private BasketDTO basket;


            private List<Long> billIds;



}