package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Picture;

public interface PictureDao extends JpaRepository<Picture, Long> {
}
