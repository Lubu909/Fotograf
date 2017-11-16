package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
