package uns.ac.rs.mbrs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovvDTO {

    private long id;

            private String name;
            private SzagorDTO szagor;


}