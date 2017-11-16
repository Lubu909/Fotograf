package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.dao.ScoreDao;
import pl.edu.ug.model.Score;

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
    public List<Score> getAll() {
        return scoreDao.findAll();
    }
}
