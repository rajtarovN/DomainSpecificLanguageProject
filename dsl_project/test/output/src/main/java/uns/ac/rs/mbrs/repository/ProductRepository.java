package uns.ac.rs.mbrs.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.mbrs.model.*;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

                        List<Product> findAllByPersonId(Long id);



}