
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
@Table(name = "itemwithprice")
@Getter
@Setter
public class ItemWithPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="currentprice")
    private double currentPrice;

    @Column(name="iscurrent")
    private boolean iscurrent; //is it still valid

            @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemWithPrice")
            @JsonIgnoreProperties(value = "person", allowSetters = true)
            private List<Bill>  bill;

               @ManyToOne
                (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
                private Item  item;

    @Column(name="deleted", unique = false)
    private boolean deleted;


    public ItemWithPrice() {}

    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}