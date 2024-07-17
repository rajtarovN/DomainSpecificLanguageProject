
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
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

                @ElementCollection
                @CollectionTable(name = "basket_quantities", joinColumns = @JoinColumn(name = "basket_id"))
                @Column(name = "quantity")
                private List<Integer> quantity;

                @ManyToMany

                 (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "basket"         )

                                @JsonIgnoreProperties(value = "person", allowSetters = true)
                private List<Item>  item;


    @Column(name="formular")
    private String formular;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToOne
        @JoinColumn
        (
        )
                    @JsonIgnoreProperties(value = "bill", allowSetters = true)

        private Person person;

    public Basket() {}

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