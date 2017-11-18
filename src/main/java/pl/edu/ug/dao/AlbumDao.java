package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Album;

public interface AlbumDao extends JpaRepository<Album, Long> {
}
