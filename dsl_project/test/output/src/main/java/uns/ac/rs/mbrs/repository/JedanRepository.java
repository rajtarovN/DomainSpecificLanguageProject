package uns.ac.rs.mbrs.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.mbrs.model.*;
import java.util.List;

@Repository
public interface JedanRepository extends JpaRepository<Jedan, Long> {

                        List<Jedan> findAllByTriId(Long id);


                        List<Jedan> findAllByValjdaId(Long id);




}