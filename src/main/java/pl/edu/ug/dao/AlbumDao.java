package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.User;

import java.util.List;

public interface AlbumDao extends JpaRepository<Album, Long> {
    Album findByName(String name);
    Album findByNameAndAuthor(String name, User user);
    List<Album> findByAuthor(User user);
}
