package pl.edu.ug.service;

import org.springframework.data.jpa.domain.Specification;
import pl.edu.ug.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> getUsers();
    List<User> search(Specification<User> userSpecification);

    void delete(Long id);
    void delete(User user);
    void deleteById(Long id);

    User getOne(Long id);

}
