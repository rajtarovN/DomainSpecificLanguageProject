
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
@Table(name = "item")
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
            @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "item")

            private List<ItemWithPrice>  itemWithPrice;
                @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
                @JoinTable(
                        name = "item_basket",
                        joinColumns = @JoinColumn(name = "item_id"),
                        inverseJoinColumns = @JoinColumn(name = "basket_id")
                )

                                @JsonIgnoreProperties(value = "person", allowSetters = true)
                private List<Basket>  basket;



    @Column(name="name")
    private String name;

    @Column(name="quantity")
    private int quantity;
    @Column(name="deleted", unique = false)
    private boolean deleted;


    public Item() {}

    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}