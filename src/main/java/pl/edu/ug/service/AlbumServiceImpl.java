package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.ug.dao.AlbumDao;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.User;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Transactional(propagation = Propagation.SUPPORTS)
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
    public Album findByName(String name) {
        return albumDao.findByName(name);
    }

    @Override
    public Album findByName(String name, User author) {
        return albumDao.findByNameAndAuthor(name,author);
    }

    @Override
    public List<Album> getAll() {
        return albumDao.findAll();
    }

    @Override
    public List<Album> getAll(User user) {
        return albumDao.findByAuthor(user);
    }
}
