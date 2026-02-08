package uns.ac.rs.mbrs.dtos;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String username;
    private String password;
    private boolean loggedFirstTime;
    private String role;

}