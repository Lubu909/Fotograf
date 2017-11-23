package pl.edu.ug.service;

import pl.edu.ug.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> getUsers();

    void delete(Long id);
    void delete(User user);
    void deleteById(Long id);

    User getOne(Long id);

}
