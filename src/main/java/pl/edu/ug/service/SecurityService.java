package pl.edu.ug.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

}
