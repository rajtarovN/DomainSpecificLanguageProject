package uns.ac.rs.mbrs.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uns.ac.rs.mbrs.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

    Optional<UserRole> findById(@Param("id") long id);

    @Query("select res from UserRole res left join fetch res.users w where w.username =?1")
    Optional<UserRole> findByUsername(String username);

    Optional<UserRole> findByName(@Param("name") String name);

}