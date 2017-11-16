package pl.edu.ug.service;

import pl.edu.ug.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

}
