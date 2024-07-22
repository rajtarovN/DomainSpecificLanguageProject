
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
@Table(name = "dva")
@Getter
@Setter
public  class Dva  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="sds")
    private String sds;
    @Column(name="deleted", unique = false)
    private boolean deleted;


            @OneToMany
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
 ,mappedBy = "valjda"         )
                    @JsonIgnoreProperties(value = "tri", allowSetters = true)

        private List<Jedan>  jedan;

            @ManyToMany
        (
                cascade = CascadeType.ALL
,                 fetch = FetchType.LAZY
         )
         @JoinTable(
                    name = "dva_tri",
                    joinColumns = @JoinColumn(name = "dva_id"),
                    inverseJoinColumns = @JoinColumn(name = "tri_id")
            )
                    @JsonIgnoreProperties(value = "jedan", allowSetters = true)

        private List<Tri>  tri;

    public Dva() {}

            public  List< Jedan > getJedan() {
                return jedan;
            }
            public void setJedan(
                List<
           Jedan
                >
             jedan
            ) {
                this.jedan = jedan;
            }
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
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}