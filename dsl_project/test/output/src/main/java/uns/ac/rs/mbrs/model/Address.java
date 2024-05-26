
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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="street")
    private String street;

    @Column(name="city")
    private String city;

    @Column(name="zipcode")
    private String zipCode;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToMany
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
                 ,mappedBy = "address"        )
                    @JsonIgnoreProperties(value = "product", allowSetters = true)

        private List<Person>  probannn;

    public Address() {}

            public  List< Person > getProbannn() {
                return probannn;
            }
            public void setProbannn(
                List<
           Person
                >
            probannn
            ) {
                this.probannn = probannn;
            }
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}