
package uns.ac.rs.mbrs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Where;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import java.util.Date;
import uns.ac.rs.mbrs.model.*;
import lombok.Getter;
import lombok.Setter;
import uns.ac.rs.mbrs.dtos.LoginDTO;

@AllArgsConstructor
@Entity
@Where(clause = "deleted = false")
@Table(name = "seller")
@Getter
@Setter
public  class Seller  extends User {
    @Column(name="deleted", unique = false)
    private boolean deleted;


    public Seller() {}
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
     public Seller(LoginDTO newUserDTO) {
        this.setDeleted(false);
        this.setUsername(newUserDTO.getUsername());
    }
}