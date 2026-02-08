
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

@AllArgsConstructor
@Entity
@Where(clause = "deleted = false")
@Table(name = "address")
@Getter
@Setter
public  class Address  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="street")
    private String street;

    @Column(name="number")
    private String number;

    @Column(name="zip")
    private String zip;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToOne
        @JoinColumn(name = "bill_id")                    @JsonIgnoreProperties(value = "c, address", allowSetters = true)

        private Bill bill;

    public Address() {}
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}