package uns.ac.rs.mbrs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.mbrs.model.*;
import java.util.List;
import java.util.Date;

@Repository
public interface DvaRepository extends JpaRepository<Dva, Long> {

                        List<Dva> findAllByJedanId(Long id);


                        List<Dva> findAllByTriId(Long id);



}