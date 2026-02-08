
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
@Table(name = "basket")
@Getter
@Setter
public  class Basket  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

                @ElementCollection
                @CollectionTable(name = "basket_quantities", joinColumns = @JoinColumn(name = "basket_id"))
                @Column(name = "quantity")
                private List<Integer> quantity;
            
                @ManyToMany

                 (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "basket"         )

                                @JsonIgnoreProperties(value = "address, customer", allowSetters = true)
                private List<Item>  item;

    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToOne
                    @JsonIgnoreProperties(value = "b, basket", allowSetters = true)

        private Customer customer;

    public Basket() {}
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}