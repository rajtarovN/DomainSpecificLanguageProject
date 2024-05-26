
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
@Table(name = "novv")
@Getter
@Setter
public class Novv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToOne
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
 ,mappedBy = "novv"         )

        private Szagor szagor;

    public Novv() {}

            public Szagor  getSzagor() {
                return szagor;
            }
            public void setSzagor(
           Szagor
             szagor
            ) {
                this.szagor = szagor;
            }
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}