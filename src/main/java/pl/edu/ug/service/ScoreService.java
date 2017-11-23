package pl.edu.ug.service;

import pl.edu.ug.model.Album;
import pl.edu.ug.model.Score;
import pl.edu.ug.model.User;

import java.util.List;

public interface ScoreService {
    void add(Score score);
    void delete(Score score);
    void delete(Long id);
    Score get(Long id);
    Score getUserScore(Album album, User author);
    List<Score> getGlobalScoresList(Album album);
    double getGlobalScore(Album album);
    List<Score> getAll();
}
