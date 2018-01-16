package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
}
