
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
@Table(name = "tri")
@Getter
@Setter
public class Tri {
    @Id
    @GeneratedValue
    private long id;

    @Column(name="sdssdfe")
    private String sdssdfe;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @ManyToOne
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
        )
                    @JsonIgnoreProperties(value = "dva", allowSetters = true)

        private Jedan jedan;

            @ManyToMany
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
, mappedBy = "tri"         )
                    @JsonIgnoreProperties(value = "jedan", allowSetters = true)

        private List<Dva>  dva;

    public Tri() {}

            public Jedan  getJedan() {
                return jedan;
            }
            public void setJedan(
           Jedan
             jedan
            ) {
                this.jedan = jedan;
            }
            public  List< Dva > getDva() {
                return dva;
            }
            public void setDva(
                List<
           Dva
                >
             dva
            ) {
                this.dva = dva;
            }
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}