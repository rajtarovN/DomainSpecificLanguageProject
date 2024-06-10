
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
@Table(name = "person")
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="lastname")
    private String lastName;

    @Column(name="username")
    private String username;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToOne
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
 ,mappedBy = "person"         )

        private Basket basket;

            @OneToMany
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
                 ,mappedBy = "person"        )

        private List<Bill>  bill;

    public Person() {}

            public Basket  getBasket() {
                return basket;
            }
            public void setBasket(
           Basket
             basket
            ) {
                this.basket = basket;
            }
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
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}