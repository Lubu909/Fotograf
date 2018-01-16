package pl.edu.ug.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.User;

import java.util.List;

public interface AlbumDao extends JpaRepository<Album, Long> {
    Album findByName(String name);
    Album findByNameAndAuthor(String name, User user);
    List<Album> findByAuthor(User user);

    @Query("SELECT a from Album a left join a.scores s group by a ORDER BY AVG(s.value) desc")
    List<Album> findAllOrderByScores();
}
