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
public class SellerDTO {

    private long id;
    private String username;
    private String password;
    private boolean deleted;
    private boolean loggedFirstTime;
    private String role;




}