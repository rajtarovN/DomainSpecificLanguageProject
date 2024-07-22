
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
@Table(name = "jedan")
@Getter
@Setter
public  class Jedan  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="street")
    private String street;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToMany
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
                 ,mappedBy = "jedan"        )
                    @JsonIgnoreProperties(value = "dva", allowSetters = true)

        private List<Tri>  tri;

            @ManyToOne
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
        )
                    @JsonIgnoreProperties(value = "tri", allowSetters = true)

        private Dva valjda;

    public Jedan() {}

            public  List< Tri > getTri() {
                return tri;
            }
            public void setTri(
                List<
           Tri
                >
             tri
            ) {
                this.tri = tri;
            }
            public Dva  getValjda() {
                return valjda;
            }
            public void setValjda(
           Dva
            valjda
            ) {
                this.valjda = valjda;
            }
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}