
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
@Table(name = "bill")
@Getter
@Setter
public class Bill {
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


    @Column(name="neki_tekst")
    private String neki_tekst;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @ManyToOne
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
        )
                    @JsonIgnoreProperties(value = "basket", allowSetters = true)

        private Person person;

    public Bill() {}

            public Person  getPerson() {
                return person;
            }
            public void setPerson(
           Person
             person
            ) {
                this.person = person;
            }
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}