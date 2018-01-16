package pl.edu.ug.service;

import pl.edu.ug.model.Album;
import pl.edu.ug.model.User;

import java.util.List;

public interface AlbumService {
    void add(Album album);
    void delete(Album album);
    void delete(Long id);
    Album get(Long id);
    Album findByName(String name);
    Album findByName(String name, User author);
    List<Album> getAll();
    List<Album> getAll(User user);
    List<Album> getBest(int quantity);
}
