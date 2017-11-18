package pl.edu.ug.service;

import pl.edu.ug.model.Album;

import java.util.List;

public interface AlbumService {
    void add(Album album);
    void delete(Album album);
    void delete(Long id);
    Album get(Long id);
    List<Album> getAll();
}
