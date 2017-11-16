package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.dao.PictureDao;
import pl.edu.ug.model.Picture;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public void add(Picture picture) {
        pictureDao.save(picture);
    }

    @Override
    public void delete(Picture picture) {
        pictureDao.delete(picture);
    }

    @Override
    public void delete(Long id) {
        pictureDao.delete(id);
    }

    @Override
    public Picture get(Long id) {
        return pictureDao.getOne(id);
    }

    @Override
    public List<Picture> getAll() {
        return pictureDao.findAll();
    }
}
