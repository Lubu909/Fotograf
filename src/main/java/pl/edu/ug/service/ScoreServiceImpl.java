package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.dao.ScoreDao;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Score;
import pl.edu.ug.model.User;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    @Override
    public void add(Score score) {
        scoreDao.save(score);
    }

    @Override
    public void delete(Score score) {
        scoreDao.delete(score);
    }

    @Override
    public void delete(Long id) {
        scoreDao.delete(id);
    }

    @Override
    public Score get(Long id) {
        return scoreDao.getOne(id);
    }

    @Override
    public Score getUserScore(Album album, User author) {
        return scoreDao.getByAlbumAndAuthor(album,author);
    }

    @Override
    public List<Score> getGlobalScoresList(Album album) {
        return scoreDao.getByAlbum(album);
    }

    @Override
    public double getGlobalScore(Album album) {
        List<Score> list = scoreDao.getByAlbum(album);
        if(!list.isEmpty())
            return list.stream().mapToDouble(s -> s.getValue()).average().getAsDouble();
        else
            return 0;
    }

    @Override
    public List<Score> getAll() {
        return scoreDao.findAll();
    }
}
