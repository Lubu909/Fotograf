package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.dao.AlbumDao;
import pl.edu.ug.model.Album;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Override
    public void add(Album album) {
        albumDao.save(album);
    }

    @Override
    public void delete(Album album) {
        albumDao.delete(album);
    }

    @Override
    public void delete(Long id) {
        albumDao.delete(id);
    }

    @Override
    public Album get(Long id) {
        return albumDao.getOne(id);
    }

    @Override
    public List<Album> getAll() {
        return albumDao.findAll();
    }
}
