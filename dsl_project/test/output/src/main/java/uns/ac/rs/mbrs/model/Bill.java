
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
@Table(name = "bill")
@Getter
@Setter
public  class Bill  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="total_price")
    private double totalPrice;


            @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
            @JoinTable(
                    name = "bill_itemWithPrice",
                    joinColumns = @JoinColumn(name = "bill_id"),
                    inverseJoinColumns = @JoinColumn(name = "itemWithPrice_id")
            )
    private List<ItemWithPrice>  itemWithPrice;


    @Column(name="cashier")
    private String cashier;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToOne
                    @JsonIgnoreProperties(value = "bill", allowSetters = true)

        private Address address;

            @ManyToOne
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
        )
                    @JsonIgnoreProperties(value = "b, bill", allowSetters = true)

        private Customer customer;

    public Bill() {}
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}