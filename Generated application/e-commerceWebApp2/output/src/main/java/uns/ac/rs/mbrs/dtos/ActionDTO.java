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
public class ActionDTO {

    private long id;
    private Date dateFrom;
    private Date dateTo;
    private String originalCode;
    private String transformedCode;

            private String name;

            private List<Long> itemIds;




}