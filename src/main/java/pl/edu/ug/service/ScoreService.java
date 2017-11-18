package pl.edu.ug.service;

import pl.edu.ug.model.Score;

import java.util.List;

public interface ScoreService {
    void add(Score score);
    void delete(Score score);
    void delete(Long id);
    Score get(Long id);
    List<Score> getAll();
}
