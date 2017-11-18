package pl.edu.ug.service;

import pl.edu.ug.model.User;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

}
