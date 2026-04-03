package pl.publicprojects.javawebpanel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.publicprojects.javawebpanel.session.Role;
import pl.publicprojects.javawebpanel.session.Session;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
