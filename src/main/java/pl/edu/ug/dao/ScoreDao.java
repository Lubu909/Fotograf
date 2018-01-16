package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Score;
import pl.edu.ug.model.User;

import java.util.List;

public interface ScoreDao extends JpaRepository<Score, Long> {
    List<Score> getByAlbum(Album album);
    Score getByAlbumAndAuthor(Album album, User author);
}
