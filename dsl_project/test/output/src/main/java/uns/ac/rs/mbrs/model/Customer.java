
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

        private List<Bill>  bill;

            @OneToOne
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
 ,mappedBy = "customer"         )

        private Basket basket;

    public Customer() {}

            public  List< Bill > getBill() {
                return bill;
            }
            public void setBill(
                List<
           Bill
                >
            bill
            ) {
                this.bill = bill;
            }
            public Basket  getBasket() {
                return basket;
            }
            public void setBasket(
           Basket
            basket
            ) {
                this.basket = basket;
            }
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}