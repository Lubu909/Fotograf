package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Score;

public interface ScoreDao extends JpaRepository<Score, Long> {
}
