
package uns.ac.rs.mbrs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Where;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import java.util.Date;

import uns.ac.rs.mbrs.dtos.LoginDTO;
import uns.ac.rs.mbrs.model.*;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Where(clause = "deleted = false")
@Table(name = "customer")
@Getter
@Setter
public  class Customer  extends User {
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToMany
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
        )
                    @JsonIgnoreProperties(value = "a, customer", allowSetters = true)

        private List<Bill>  bill;

            @OneToOne
                    @JsonIgnoreProperties(value = "customer", allowSetters = true)

        private Basket basket;

    public Customer() {}

    public Customer(LoginDTO newUserDTO) {
        this.deleted = false;
        this.setUsername(newUserDTO.getUsername());
        this.setPassword(newUserDTO.getPassword());
    }

    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}